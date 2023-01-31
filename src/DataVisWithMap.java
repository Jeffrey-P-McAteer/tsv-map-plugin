import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import nddv.data.DatasetLoader;
import nddv.gui.ATSVFrontend;
import nddv.gui.ATSVNoSplashScreenNoCloseOperation;
import nddv.gui.AbstractDetailWindow;
import nddv.gui.DetailWindow;
import nddv.gui.mainframe.ATSVMainFrameTemplate;
import nddv.gui.mainframe.basicdisplay.ATSVBasicMainFrameTemplate;
import nddv.validator.ObjectCreator;

import java.lang.reflect.*;

public class DataVisWithMap {
  private static ATSVFrontend ui_frontend = null;

  public static void main(String[] args) {
    do_ui_modifications_async();
    try {
      UIManager.setLookAndFeel(new WindowsLookAndFeel());
    } catch (UnsupportedLookAndFeelException e) {
      System.out.println("Does Not Support Windows Look and Feel");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    new ObjectCreator();
    if (args.length == 0) {
      ui_frontend = new ATSVFrontend();
    } else if (args.length == 1 && args[0].equals("mainFrame")) {
      ATSVBasicMainFrameTemplate mainframe = new ATSVBasicMainFrameTemplate(3);
      mainframe.getMainFrame().setDetailWindow((AbstractDetailWindow)new DetailWindow());
      try {
        mainframe.getMainFrame().loadNewDesignSpace(
            DatasetLoader.load(new File("../data/carDatabase/CarData-updated.csv"), 10));
      } catch (Exception e) {
        try {
          mainframe.getMainFrame().loadNewDesignSpace(
              DatasetLoader.load(new File("./data/carDatabase/CarData-updated.csv"), 10));
        } catch (Exception f) {
          f.printStackTrace();
        } 
      } 
    } else if (args.length == 1 && args[0].equals("noSplashNoSystemExit")) {
      new ATSVNoSplashScreenNoCloseOperation();
    } else if (args.length == 1 && args[0].equals("mainFrameSystemExitonClose")) {
      new ATSVMainFrameTemplate(1);
    } else if (args.length == 1) {
      ui_frontend = new ATSVFrontend(args[0]);
    } else if (args.length == 2 && args[0].equals("engine")) {
      ui_frontend = new ATSVFrontend(args[1], true);
    } else {
      ui_frontend = new ATSVFrontend();
    } 
  }

  public static void do_ui_modifications_async() {
    new Thread(() -> {
      try { Thread.sleep(5000); } catch (Exception e) { } // wait 5s for UI to initialize
      javax.swing.SwingUtilities.invokeLater(() -> {
        try {
          // DataVisWithMap.<java.util.Vector<nddv.data.menu.ATSVMenuControls>>reflect_get(ui_frontend, "menuMiscControls").add(new nddv.data.menu.ATSVMenuControls(){
          //   { // Anon initializer
          //     this.menuItem = new javax.swing.JMenuItem("Some nonsense");
          //   }
          // });
          // DataVisWithMap.<Void>reflect_call(ui_frontend, "activateMenuItems", true); // reads menuMiscControls & creates UI
          // System.out.println("UI modified!");
          javax.swing.JMenuBar app_menu_bar = ui_frontend.getJMenuBar();
          ((javax.swing.JMenu) app_menu_bar.getSubElements()[4]).add(
            new javax.swing.JMenuItem("Some nonsense")
          );

        }
        catch (Exception e) {
          System.out.println("do_ui_modifications_async e = "+e);
        }
      });
    }).start();
  }

  public static <T> T reflect_get(Object o, String field_name) { // instance
    try {
      Field f = o.getClass().getDeclaredField(field_name);
      f.setAccessible(true);
      return (T) f.get(o);
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static <T> T reflect_get(Class<?> c, String field_name) { // statics
    try {
      Field f = c.getDeclaredField(field_name);
      f.setAccessible(true);
      return (T) f.get(c);
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static <T> T reflect_call(Object o, String method_name, Object... args) { // statics
    Class<?>[] arg_classes = new Class<?>[args.length];
    try {
      for (int i=0; i<args.length; i+=1) {
        arg_classes[i] = args[i].getClass();
      }
      Method m = o.getClass().getDeclaredMethod(method_name, arg_classes);
      m.setAccessible(true);
      return (T) m.invoke(o, args);
    }
    catch (Exception e) {
      if (e instanceof java.lang.NoSuchMethodException) {
        // If any of arg_classes is Boolean, Integer, Double, down-cast to primitive and try again.
        boolean down_casted_an_arg = false;
        for (int i=0; i<args.length; i+=1) {
          if (arg_classes[i] == Boolean.class) {
            arg_classes[i] = boolean.class;
            down_casted_an_arg = true;
          }
          else if (arg_classes[i] == Integer.class) {
            arg_classes[i] = int.class;
            down_casted_an_arg = true;
          }
          else if (arg_classes[i] == Double.class) {
            arg_classes[i] = double.class;
            down_casted_an_arg = true;
          }
        }
        if (down_casted_an_arg) {
          try {
            Method m = o.getClass().getDeclaredMethod(method_name, arg_classes);
            m.setAccessible(true);
            return (T) m.invoke(o, args);
          }
          catch (Exception e2) {
            System.out.println(e2);
          }
        }
      }
      else {
        System.out.println(e);
      }
    }
    return null;
  }


}
