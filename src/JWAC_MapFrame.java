
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import nddv.brushpreference.guicontrols.BrushPreferenceSelectionFrame;
import nddv.data.DesignSpace;
import nddv.data.columnData.Partition;
import nddv.data.listeners.Listener;
import nddv.data.session.SessionObjects;
import nddv.useroperations.ExportStringtoFile;

import javax.swing.JToolBar;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JWAC_MapFrame extends JFrame implements Listener {
  private JWAC_MapPanel mapPanel;
  
  protected JButton brushSelection = new JButton(new ImageIcon(getClass().getResource("/images/linked.jpg")));
  
  private JPanel brushesPanel = new JPanel();
  
  private JPanel panelDisplay;
  
  public JWAC_MapFrame(DesignSpace desSpace) {
    initializeFrame();
    setDesignSpace(desSpace);
    //setMaximumSize(new Dimension(600, 600));
    //pack();
  }
  
  public void initializeFrame() {
    setSize(600, 600);
    this.panelDisplay = new JPanel();
    this.panelDisplay.setLayout(new BorderLayout());
    this.mapPanel = new JWAC_MapPanel();
    this.mapPanel.setParentFrame(this);
    //this.mapPanel.getToolBar().add(Box.createHorizontalStrut(10));
    
    // JScrollPane pane = new JScrollPane(this.mapPanel);
    // pane.setSize(new Dimension(400, 400));
    this.panelDisplay.add(this.mapPanel, "Center");
    this.panelDisplay.add((Component)this.mapPanel.getToolBar(), "North");
    this.panelDisplay.add(this.brushesPanel, "South");
    setLocation(0, 100);
    getContentPane().add(this.panelDisplay);
    this.setSize(750, 500);
    pack();
    setVisible(false);
    this.setSize(750, 500);
  }
  
  public JPanel getPanelDisplay() {
    return this.panelDisplay;
  }
  
  public void setDesignSpace(DesignSpace ds) {
    ds.registerListener(this);
    this.mapPanel.setDesignSpace(ds);
    setTitle("JWAC Map Plots : " + ds.getPointerToData());
    addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            //HistogramFrame.this.deletePlot();
          }
        });
    setLinkedIcon();
  }
  
//   public HistogramPanel getHistogramPanel() {
//     return null; // return this.histogramPanel;
//   }
  
  public void deletePlot() {
    // this.histogramPanel.getDesignSpace().removeListener(this);
    // this.histogramPanel.deletePlot();
    dispose();
  }
  
  public void setLinkedIcon() {
    boolean linked = true;
    if (SessionObjects.getBrushPreferenceFrame() == null) {
      linked = false;
    } else if (SessionObjects.getBrushPreferenceFrame().getDesignSpace() == null) {
      linked = false;
    } else {
      String linkedIdentifier = SessionObjects.getBrushPreferenceFrame().getDesignSpace().getIdentifier();
      //linked = this.histogramPanel.getDesignSpace().getIdentifier().equals(linkedIdentifier);
    } 
    //ImageIcon icon = this.histogramPanel.getDesignSpace().getCustomLinkedIcon();
    ImageIcon icon = null;
    if (icon != null) {
      this.brushSelection.setIcon(icon);
    } else if (linked) {
      this.brushSelection.setIcon(new ImageIcon(getClass().getResource("/images/linked.jpg")));
    } else {
      this.brushSelection.setIcon(new ImageIcon(getClass().getResource("/images/notlinked.jpg")));
    } 
    this.brushSelection.revalidate();
  }
  
  private void displayBrushPrefSelection() {
    // if (this.histogramPanel.getDesignSpace().getCustomLinkedIcon() != null)
    //  return; 
    // final BrushPreferenceSelectionFrame dialog = new BrushPreferenceSelectionFrame((JComponent)this.histogramPanel.getToolBar(), false);
    // if (dialog.getBoxes() != null) {
    //   JCheckBox[] boxes = dialog.getBoxes();
    //   for (int i = 0; i < boxes.length; i++) {
    //     boxes[i].addMouseListener(new MouseAdapter() {
    //           public void mouseClicked(MouseEvent e) {
    //             HistogramFrame.this.brushChangeSelection(dialog);
    //           }
    //         });
    //   } 
    // } 
  }
  
  private void brushChangeSelection(BrushPreferenceSelectionFrame dialog) {
    DesignSpace newDs = dialog.getDesignSpace();
    if (newDs == null)
      return; 
    // this.histogramPanel.getDesignSpace().removeListener(this);
    // this.histogramPanel.setDesignSpace(newDs);
    // this.histogramPanel.getDesignSpace().registerListener(this);
    // this.histogramPanel.refreshPanel();
    setLinkedIcon();
    refreshImageOfBrushControls();
  }
  
  public void refreshImageOfBrushControls() {
    this.brushesPanel.removeAll();
    // if (this.histogramPanel.getModel().isDisplayBrushes())
    //   this.brushesPanel.add(this.histogramPanel.getDesignSpace().getImageofControls()); 
    this.brushesPanel.revalidate();
  }
  
  public void actionBrushorPreferenceChange() {
    // if (!this.histogramPanel.getVectorofVariables().contains("Preference Shading")) {
    //   this.histogramPanel.brushUpdate();
    //   if (this.histogramPanel.getModel().isDisplayBrushes())
    //     refreshImageOfBrushControls(); 
    // } else {
    //   this.histogramPanel.refreshPanel();
    // } 
  }
  
  public void columnModelIsModified(DesignSpace desSpace, String options) {
    // this.histogramPanel.setDesignSpace(desSpace);
    // this.histogramPanel.refreshPanel();
  }
  
  public void frontendHasBeenIconified(boolean min) {}
  
  public void newFileHasBeenLoaded() {
    deletePlot();
  }
  
  public void pickOperation(String type) {
    //this.histogramPanel.brushUpdate();
  }
  
  public void updateBrushPreferenceControls() {}
  
  public void linkPlotChange() {
    setLinkedIcon();
  }
  
  public void attractorUpdate() {}
  
  public void runtimeExceptionOccurred() {}
  
  public void savePlotToFile(File file) {
    //ExportStringtoFile.exportStringtoFile(this.histogramPanel.toStringWithData(), file, true);
  }
  
  public void savePlotTamplatesToFile(File file) {
    //ExportStringtoFile.exportStringtoFile(this.histogramPanel.getHistogramPlotConfiguration().asXML(), file, true);
  }
  
  public void setListenerToDefaultDS() {
    // DesignSpace ds = this.histogramPanel.getDesignSpace();
    // ds.removeListener(this);
    // ds = SessionObjects.getDefaultDS();
    // ds.registerListener(this);
    // columnModelIsModified(ds, (String)null);
    setLinkedIcon();
    refreshImageOfBrushControls();
  }
  
  public void annotationChange() {}
  
  public void paintPlot() {
    //this.histogramPanel.paintImmediately(this.histogramPanel.getBounds());
  }
  
  public void switchToDesignSpace(DesignSpace ds) {}
  
  public Vector<Partition> getSelectedPartitions() {
    //return this.histogramPanel.getModel().getSelectedPartitions();
    return new Vector<Partition>();
  }

  public static class JWAC_MapPanel extends JPanel implements ActionListener {
      private JToolBar toolbar; // above us in UI parent
      private JFrame parentFrame;

      private DesignSpace ds;

      private com.kitfox.svg.SVGUniverse svg_universe;
      //private com.kitfox.svg.SVGDisplayPanel svg_view;

      public JWAC_MapPanel() {
          this.setLayout(new BorderLayout());

          this.toolbar = new JToolBar();
          this.constructMapToolbar(this.toolbar);

          this.svg_universe = new com.kitfox.svg.SVGUniverse();
          this.constructPanelUI(this.svg_universe);

          this.setSize(750, 500);
      }

      private void constructMapToolbar(JToolBar toolbar) {
        // JButton test_btn = new JButton("Test Button");
        // test_btn.addActionListener((event) -> {
        //   System.out.println("test_btn clicked! event="+event);
        // });
        // toolbar.add(test_btn);

        // JLabel scale_label = new JLabel("Map scale: ");
        // toolbar.add(scale_label);
        // JSlider scale_slider = new JSlider();
        // scale_slider.setMinimum(0);
        // scale_slider.setMaximum(100);
        // scale_slider.setValue(50);
        // scale_slider.setOrientation(JSlider.HORIZONTAL);
        // scale_slider.setPreferredSize(new Dimension(200, 20));
        // scale_slider.addChangeListener((evt) -> {
        //   if (this.svg_view != null) {
        //     this.svg_view.setScale( (float) scale_slider.getValue() / 100.0f );
        //   }
        // });

        // toolbar.add(scale_slider);
      }

      private void constructPanelUI(com.kitfox.svg.SVGUniverse svg_universe) {
          try {
            // java.net.URI svg = svg_universe.loadSVG(
            //     //new java.net.URL("https://upload.wikimedia.org/wikipedia/commons/4/4d/BlankMap-World.svg")
            //     new java.net.URL("https://simplemaps.com/static/demos/resources/svg-library/svgs/world.svg")
            // );

            // java.net.URI svg = svg_universe.loadSVG( // From https://upload.wikimedia.org/wikipedia/commons/8/80/World_map_-_low_resolution.svg
            //   getClass().getResource("/simple_world_map.svg")
            // );

            //com.kitfox.svg.SVGDiagram diagram = svg_universe.getDiagram(svg);

            // svg_view = new com.kitfox.svg.SVGDisplayPanel();
            // svg_view.setDiagram(diagram);
            // svg_view.setScale(0.5); // to match scale bar init
            // this.add(svg_view);

            com.kitfox.svg.app.beans.SVGPanel svg_panel = new com.kitfox.svg.app.beans.SVGPanel();
            //svg_panel.setSvgURI(svg);
            svg_panel.setSvgResourcePath("/simple_world_map.svg");
            svg_panel.setAutosize(com.kitfox.svg.app.beans.SVGPanel.AUTOSIZE_STRETCH);
            
            //svg_panel.setSize(200, 200);
            //svg_panel.setPreferredSize(new Dimension(200, 200));

            this.add(svg_panel, BorderLayout.CENTER);

            this.repaint();

          }
          catch (Exception e) {
            e.printStackTrace();
          }
      }

      public void actionPerformed(ActionEvent e) {
          System.out.println("JWAC_MapPanel actionPerformed e="+e);
          
      }

      public void setParentFrame(JFrame parentFrame) {
          this.parentFrame = parentFrame;
      }

      public JToolBar getToolBar() {
          return this.toolbar;
      }

      public void setDesignSpace(DesignSpace ds) {
        this.ds = ds;
        // TODO listen to events?
        
      }



  }
}
