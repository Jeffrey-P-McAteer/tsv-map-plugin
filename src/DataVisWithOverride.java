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

public class DataVisWithOverride {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(new WindowsLookAndFeel());
    } catch (UnsupportedLookAndFeelException e) {
      System.out.println("Does Not Support Windows Look and Feel");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    new ObjectCreator();
    if (args.length == 0) {
      new JWAC_ATSVFrontend();
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
      new JWAC_ATSVFrontend(args[0]);
    } else if (args.length == 2 && args[0].equals("engine")) {
      new JWAC_ATSVFrontend(args[1], true);
    } else {
      new JWAC_ATSVFrontend();
    } 
  }
}
