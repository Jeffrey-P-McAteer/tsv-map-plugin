
import nddv.gui.*;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import nddv.ahp.MenuAHPWizard;
import nddv.attractors.engine.EngineFrame;
import nddv.attractors.engine.MenuConfiguration;
import nddv.attractors.engine.VisualizationLogger;
import nddv.attractors.glyphplots.MenuCubeGlyphWithAttractors;
import nddv.attractors.glyphplots.MenuStarGlyphWithAttractors;
import nddv.attractors.histogram2D.MenuHistogram2DWithAttractors;
import nddv.attractors.parallelcoordinates.MenuParCoordWithAttractors;
import nddv.attractors.scattermatrix.MenuScatterMatrixWithAttractors;
import nddv.attractors.scatterplot.MenuScatterPlotWithAttractors;
import nddv.attractors.storage.SessionAttractors;
import nddv.brushpreference.MenuDefaultBrushPreference;
import nddv.carpetplot.MenuCarpetPlot;
import nddv.cluster.MenuKMeansAlgorithm;
import nddv.cluster.MenuKMeansPCAAlgorithm;
import nddv.cluster.MenuKModesAlgorithm;
import nddv.combinationplots.MenuBarChartDisplay;
import nddv.comparisonplots.MenuComparisonWizard;
import nddv.config.ConfigurationReader;
import nddv.data.DatasetLoader;
import nddv.data.DesignSpace;
import nddv.data.annotations.notes.NoteHelper;
import nddv.data.annotations.pathgenerator.MenuAnnotationPath;
import nddv.data.columnData.Column;
import nddv.data.columnData.ColumnModel;
import nddv.data.columnData.RecalculatableColumn;
import nddv.data.columnData.converter.ColumnConverter;
import nddv.data.filters.DataFilter;
import nddv.data.filters.TemplateFilter;
import nddv.data.menu.ATSVMenuControls;
import nddv.data.menu.MenuAppendData;
import nddv.data.menu.MenuBackupWorker;
import nddv.data.menu.MenuDesignSpaceOperations;
import nddv.data.menu.MenuDisplayDataTable;
import nddv.data.menu.MenuExit;
import nddv.data.menu.MenuExportData;
import nddv.data.menu.MenuExportPareto;
import nddv.data.menu.MenuLoadFile;
import nddv.data.menu.MenuMemoryMonitor;
import nddv.data.menu.MenuSaveSession;
import nddv.data.menu.MenuStereoCapability;
import nddv.data.menu.MenuSurrogate;
import nddv.data.menu.MenuTransposeData;
import nddv.data.menu.MenuWorkingDirectory;
import nddv.data.operations.Extension;
import nddv.data.session.SessionObjects;
import nddv.data.stat.princomp.MenuPrincipalComponents;
import nddv.database.dataaccess.ConnectionToDataBases;
import nddv.delaunay.ContourReader;
import nddv.glyphplots.MenuCylindricalCoordGlyph;
import nddv.glyphplots.MenuDotGlyph;
import nddv.glyphplots.MenuECFGlyph;
import nddv.glyphplots.MenuSphericalCoordGlyph;
import nddv.histogram.h1d.MenuHistogram;
import nddv.hybridpareto.MenuParetoDataset;
import nddv.largedata.parallelcoordinets.MenuParallelCoordinatesLargeData;
import nddv.largedata.scattermatrix.MenuScatterMatrixLargeData;
import nddv.largedata.scatterplot.MenuScatterPlotLargeData;
import nddv.largedata.scatterplot.threed.MenuBinned3DScatterPlot;
import nddv.mcdm.MenuMCDMWizard;
import nddv.scatter.onevariabletable.MenuScatterPlotComparisonView;
import nddv.statisticplots.boxwhisker.MenuBoxWhiskerPlot;
import nddv.timeseries.MenuXYTimeSeries;
import nddv.useroperations.menus.MenuAddColumn;
import nddv.useroperations.menus.MenuDiscreteVariableLimit;
import nddv.useroperations.menus.MenuNestedData;

public class JWAC_ATSVFrontend extends ATSVFrontend {
  
  public JWAC_ATSVFrontend() {
      super();
  }

  public JWAC_ATSVFrontend(String s) {
      super(s);
  }

  public JWAC_ATSVFrontend(String s, boolean b) {
      super(s, b);
  }

  @Override
  protected void setPlotVector() {
    super.setPlotVector();
    this.menuPlotControls.add(new MenuJWACMap());
  }
  
}
