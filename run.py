
import os
import sys
import subprocess
import traceback
import shutil
import glob

import urllib.request

def print_and_run(*cmd):
  print('> {}'.format(' '.join(list(cmd))))
  subprocess.run(list(cmd), check=True)

def ensure_downloaded(url, file_path):
  if not os.path.exists(file_path):
    with open(file_path, 'wb') as fd:
      fd.write(
        urllib.request.urlopen(url).read()
      )

def main(args=sys.argv):
  while len(args) > 0 and ('python' in args[0] or os.path.basename(__file__) in args[0]):
    args = args[1:]

  tsv_dir = None
  if len(args) > 0 and os.path.exists(args[0]):
    tsv_dir = args[0]

  if tsv_dir is None or not os.path.exists(tsv_dir):
    # TODO guess at windows common install paths
    pass

  if tsv_dir is None:
    tsv_dir = input('TSV directory: ')

  if tsv_dir is None or not os.path.exists(tsv_dir):
    print('Unknown TSV directory {}, exiting...'.format(tsv_dir))
    return

  print('TSV directory is: {}'.format(tsv_dir))

  tsv_path_items = [
    os.path.join(tsv_dir, 'jre', 'bin'),
    os.path.join(tsv_dir, 'jnilib'),
    os.path.join(tsv_dir, 'jnilib', 'vtk'),
    os.path.join(tsv_dir, 'dist', 'glpk-4.55'),
  ]
  os.environ['PATH'] = os.pathsep.join(tsv_path_items)+os.pathsep+os.environ['PATH']

  # Open .jar file, scan for packaged java, run app
  java_exe = None
  if shutil.which('java'):
    java_exe = shutil.which('java')

  if java_exe is None:
    java_exe = os.path.join(tsv_dir, 'jre', 'bin', 'java.exe')

  print('Java is: {}'.format(java_exe))

  if 'de' in args:
    if not os.path.exists('dist/jd-gui-1.6.6.jar'):
      print('Ensure dist/jd-gui-1.6.6.jar exists!')
      return

    print_and_run(
      java_exe, '-jar', os.path.join('dist', 'jd-gui-1.6.6.jar'), os.path.join(tsv_dir, 'dist', 'atsv.jar')
    )
    return

  # We try to compile the plugin if we have javac, else we
  # assume the plugin's .class files are all under the folder dist/*.class
  javac_exe = None
  if shutil.which('javac'):
    javac_exe = shutil.which('javac')

  tsv_map_plugin_dist = os.path.join(os.path.dirname(__file__), 'dist')

  if not os.path.exists(tsv_map_plugin_dist):
    os.makedirs(tsv_map_plugin_dist)

  # Download https://github.com/blackears/svgSalamander/releases/download/v1.1.4/svgSalamander-1.1.4.jar
  # And add it to our compile + run classpath.
  # The svg rendering library is used to (1) modify and (2) render SVGs of the world as a map graph.
  # This approach makes it trivial for users to display _any_ aoi - simply create an svg with IDs that
  # match values in a dataset.
  svg_salamander_jar = os.path.join(tsv_map_plugin_dist, 'svgSalamander-1.1.3.jar')
  ensure_downloaded(
    'https://github.com/blackears/svgSalamander/releases/download/v1.1.3/svgSalamander-1.1.3.jar',
    svg_salamander_jar
  )

  tsv_classpath = [
    os.path.join(tsv_dir, 'dist'),
    os.path.join(tsv_dir, 'dist', 'atsv.jar'),
    os.path.join(tsv_dir, 'dist', 'parser.jar'),
    svg_salamander_jar,
  ]

  if javac_exe is None:
    print()
    print('Cannot find javac, assuming plugin is already built under dist/')
    print('If map graph does not appear check the dist/ directory.')
    print()
    time.sleep(2)
  else:
    # perform build
    src_java_files = [
      x for x in glob.glob('src/**/*.java', recursive=True)
    ]
    if len(src_java_files) > 0:
      print_and_run(
        javac_exe,
          '-d', tsv_map_plugin_dist,
          '-cp', os.pathsep.join(tsv_classpath),
          *src_java_files
      )
    
    # Copy icon resource
    shutil.copy(os.path.join('src', 'JWACMapPlot.gif'), os.path.join(tsv_map_plugin_dist, 'JWACMapPlot.gif'))
    shutil.copy(os.path.join('src', 'simple_world_map.svg'), os.path.join(tsv_map_plugin_dist, 'simple_world_map.svg'))

  # Add our dist/ to the plugin class path
  tsv_classpath.insert(0, tsv_map_plugin_dist)

  print_and_run(
    java_exe,
    '-cp', os.pathsep.join(tsv_classpath),
    '-Xms4G', '-Xmx8G', '-Dsun.java2d.ddoffscreen=true', '-Dsun.java2d.gdiblit=true',
    # 'DataVis' # Name of TSV class with main() defined
    'DataVisWithOverride',
    os.path.join(tsv_dir, 'data', 'carDatabase', 'CarData-updated.csv')
  )


if __name__ == '__main__':
  main()

