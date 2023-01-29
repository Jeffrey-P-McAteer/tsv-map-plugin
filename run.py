
import os
import sys
import subprocess
import traceback

def main(args=sys.argv):
  if len(args) > 0 and 'python' in args[0]:
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

  # Open .jar file, scan for packaged java, run app

  subprocess.run([
    f'', ''
  ])


if __name__ == '__main__':
  main()

