
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.BoxLayout;
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

  private DesignSpace ds;
  
  public JWAC_MapFrame(DesignSpace desSpace) {
    this.ds = desSpace;
    initializeFrame();
    setDesignSpace(desSpace);
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
    JPanel toolbar_holder = new JPanel();
    toolbar_holder.setLayout(new BorderLayout());
    toolbar_holder.add( this.mapPanel.getToolBar(), "North" );
    this.panelDisplay.add(toolbar_holder, "West");
    this.panelDisplay.add(this.brushesPanel, "South");
    setLocation(0, 100);
    getContentPane().add(this.panelDisplay);
    this.setSize(750, 500);
    pack();
    setVisible(false);
    this.setSize(950, 500);
  }
  
  public JPanel getPanelDisplay() {
    return this.panelDisplay;
  }
  
  public void setDesignSpace(DesignSpace ds) {
    this.ds = ds;
    ds.registerListener(this);
    this.mapPanel.setDesignSpace(ds);
    setTitle("JWAC Map Plots : " + ds.getPointerToData());
    addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            //HistogramFrame.this.deletePlot();
            ds.removeListener(JWAC_MapFrame.this);
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
  
  com.kitfox.svg.SVGElement last_picked_svg_element = null;

  public void pickOperation(String type) {
    //this.histogramPanel.brushUpdate();
    System.out.println("JWAC_MapFrame.pickOperation("+type+")");

    if (last_picked_svg_element != null) {
      // reset it
      try {
        last_picked_svg_element.setAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "red");
      }
      catch (Exception e) {
        e.printStackTrace();
        try {
          last_picked_svg_element.addAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "red");
        }
        catch (Exception e2) {
          e2.printStackTrace();
        }
      }
      this.mapPanel.svg_panel.repaint();
      this.mapPanel.repaint();
    }

    if (type != null && type.equals("PointSelect")) {
      // Get selected data record
      int selected_data_idx = this.ds.getSelectedIndex();
      if (selected_data_idx < 0) {

      }
      else {
        // Get value of toolbar_map_value_selector
        int column_i_to_use = this.mapPanel.toolbar_map_value_selector.getSelectedIndex();
        String column_name_to_use = ""+this.mapPanel.toolbar_map_value_selector.getSelectedItem();

        String selected_country_name = this.ds.getColumn(column_i_to_use).getStringValue(selected_data_idx);

        System.out.println("Highlighing the the country "+selected_country_name+"");
        System.out.println("diagram should be "+this.mapPanel.svg_universe.getDiagram(this.mapPanel.svg_panel.getSvgURI()));
        // this.svg_universe.getDiagram(svg_panel.getSvgURI());

        this.mapPanel.svg_diagram = com.kitfox.svg.SVGCache.getSVGUniverse().getDiagram(this.mapPanel.svg_panel.getSvgURI()); // Wierdness that fixes painting

        com.kitfox.svg.SVGElement element = this.mapPanel.svg_diagram.getElement(selected_country_name);
        if (element == null) {
          element = this.mapPanel.svg_diagram.getElement(selected_country_name.toLowerCase());
        }

        if (element != null) {
          System.out.println("Found "+selected_country_name+", element = "+element);
          try {
            element.setAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "black");
          }
          catch (Exception e) {
            e.printStackTrace();
            try {
              element.addAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "black");
            }
            catch (Exception e2) {
              e2.printStackTrace();
            }
          }
          this.mapPanel.svg_panel.repaint();
          this.mapPanel.repaint();
        }
        else {
          System.out.println(""+selected_country_name+" does not exist in this map!");
        }

        last_picked_svg_element = element;

      }

    }

  }

  public void recolor_all_countries() {
    try {
      // Get column index, or mark all red if == -1
      int color_column_i = this.mapPanel.toolbar_map_color_field_selector.getSelectedIndex() - 1 /* idx 0 == "Constant (red)", now it's == -1 */;
      if (color_column_i >= 0) {
        // For all data strings...
        int num_rows = 0;
        List<SVGElement> row_country_elements = new List<SVGElement>();
        List<String> row_col_vals = new List<String>();
        int country_col_i_to_use = this.mapPanel.toolbar_map_value_selector.getSelectedIndex();

        for (int row_i=0; row_i < num_rows; row_i += 1) {
          String row_col_val = this.ds.getColumn(color_column_i).getStringValue(row_i);
          row_col_vals.add(row_col_val);
          String row_country_name = this.ds.getColumn(country_col_i_to_use).getStringValue(row_i);
          SVGElement row_country_elm = this.mapPanel.svg_diagram.getElement(row_country_name);
          row_country_elements.add(row_country_elm);
        }
        
        // row_col_vals holds value for each row by index, and row_country_elements
        // contains either the SVG element to change the color of OR null.
        


      }
      else {
        this.mapPanel.set_all_children_in_ds_to_red_bg();
      }
      // Finally color any selected element
      pickOperation("PointSelect");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
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
      private JWAC_MapFrame parentFrame;

      public JComboBox<String> toolbar_map_value_selector;

      private DesignSpace ds;

      public java.net.URI svg_uri;

      public com.kitfox.svg.SVGUniverse svg_universe;
      
      public com.kitfox.svg.app.beans.SVGPanel svg_panel;

      public com.kitfox.svg.SVGDiagram svg_diagram;

      public JComboBox<String> toolbar_map_color_field_selector;

      public JWAC_MapPanel() {
          this.setLayout(new BorderLayout());

          this.toolbar_map_value_selector = new JComboBox<String>();
          this.toolbar_map_color_field_selector = new JComboBox<String>();
          this.toolbar = new JToolBar();
          //this.toolbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
          this.toolbar.setLayout(new BoxLayout(this.toolbar, BoxLayout.Y_AXIS));
          //this.toolbar.setLayout(new GridLayout(3, 1, 12, 12));
          this.constructMapToolbar(this.toolbar);
          
          this.svg_universe = new com.kitfox.svg.SVGUniverse();
          this.constructPanelUI(this.svg_universe);

          this.setSize(950, 500);
      }

      private static void lockInPreferredSize(javax.swing.JComponent component) {
        component.setMaximumSize( component.getPreferredSize() );
      }

      private static JPanel flowLeftWrapper(javax.swing.JComponent component) {
        JPanel holder = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        lockInPreferredSize(component);
        holder.add(component);
        return holder;
      }

      private void constructMapToolbar(JToolBar toolbar) {
        JLabel field_label = new JLabel("Country Name Field: ");
        toolbar.add(flowLeftWrapper(field_label));

        this.toolbar_map_value_selector.setMaximumRowCount(18); // show lots of rows
        this.toolbar_map_value_selector.addActionListener((evt) -> {
          if (this.parentFrame != null) {
            this.parentFrame.pickOperation("PointSelect");
          }
        });
        toolbar.add(this.toolbar_map_value_selector);

        JButton svg_pick_btn = new JButton("Select .svg map file");
        svg_pick_btn.addActionListener((evt) -> {
          this.pick_new_svg_file();
        });
        svg_pick_btn.setBorder( javax.swing.BorderFactory.createBevelBorder(0) );
        toolbar.add(flowLeftWrapper(svg_pick_btn));

        JButton svg_reset_btn = new JButton("Reset .svg map");
        svg_reset_btn.addActionListener((evt) -> {
          this.reset_svg_file();
        });
        svg_reset_btn.setBorder( javax.swing.BorderFactory.createBevelBorder(0) );
        toolbar.add(flowLeftWrapper(svg_reset_btn));

        JButton display_ids_btn = new JButton("Display all path IDs in map");
        display_ids_btn.addActionListener((evt) -> {
          String all_child_ids = this.get_child_ids_string();
          //System.out.println("all_child_ids="+all_child_ids);
          String temp_id_file = System.getProperty("java.io.tmpdir")+File.separator+"tsv_svg_all_ids_file.txt";
          try {
            java.io.FileOutputStream outputStream = new java.io.FileOutputStream(temp_id_file);
            outputStream.write( all_child_ids.getBytes() );
            outputStream.close();
            // Tell OS to open in default handler
            java.awt.Desktop.getDesktop().edit(new File(temp_id_file));
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        });
        display_ids_btn.setBorder( javax.swing.BorderFactory.createBevelBorder(0) );
        toolbar.add(flowLeftWrapper(display_ids_btn));

        JLabel color_field_label = new JLabel("Color (not yet done)");
        toolbar.add(flowLeftWrapper(color_field_label));

        this.toolbar_map_color_field_selector.setMaximumRowCount(18); // show lots of rows
        this.toolbar_map_color_field_selector.addActionListener((evt) -> {
          if (this.parentFrame != null) {
            this.parentFrame.recolor_all_countries();
          }
        });
        toolbar.add(this.toolbar_map_color_field_selector);
        
      }

      private void pick_new_svg_file() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            public String getDescription() {
                return "SVG Documents (*.svg)";
            }
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true; // ???
                } else {
                    return f.getName().toLowerCase().endsWith(".svg");
                }
            }
        });
        int result = fileChooser.showOpenDialog(this.parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            this.svg_uri = selectedFile.toURI();
            this.svg_panel.setSvgURI(this.svg_uri);
            // Update svg_diagram for things that will read it 
            this.svg_diagram = com.kitfox.svg.SVGCache.getSVGUniverse().getDiagram(this.svg_panel.getSvgURI()); // Wierdness that fixes painting
            this.set_all_children_in_ds_to_red_bg();
        }
      }

      private void reset_svg_file() {
        try {
          this.svg_panel.setSvgResourcePath("/simple_world_map.svg");
          // Update svg_diagram for things that will read it 
          this.svg_diagram = com.kitfox.svg.SVGCache.getSVGUniverse().getDiagram(this.svg_panel.getSvgURI()); // Wierdness that fixes painting
          this.set_all_children_in_ds_to_red_bg();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }

      private String get_child_ids_string() {
        return get_child_ids_string(null);
      }

      private String get_child_ids_string(com.kitfox.svg.Group parent) {
        String child_ids = "";
        if (this.svg_diagram != null) {
          if (parent == null) {
            parent = this.svg_diagram.getRoot();
          }
          for (int i=0; i<parent.getNumChildren(); i+=1) {
            com.kitfox.svg.SVGElement child = parent.getChild(i);
            if (child instanceof com.kitfox.svg.Group) {
              child_ids += get_child_ids_string((com.kitfox.svg.Group) child);
            }
            else {
              child_ids += child.getId()+"\n";
            }
          }
        }
        return child_ids;
      }

      private void set_all_children_in_ds_to_red_bg() {
        set_all_children_in_ds_to_red_bg(null);
      }

      private void set_all_children_in_ds_to_red_bg(com.kitfox.svg.Group parent) {
        if (this.svg_diagram != null) {
          if (parent == null) {
            parent = this.svg_diagram.getRoot();
          }
          for (int i=0; i<parent.getNumChildren(); i+=1) {
            com.kitfox.svg.SVGElement child = parent.getChild(i);
            if (child instanceof com.kitfox.svg.Group) {
              set_all_children_in_ds_to_red_bg((com.kitfox.svg.Group) child);
            }
            else {

              try {
                child.setAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "red");
              }
              catch (Exception e) {
                e.printStackTrace();
                try {
                  child.addAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "red");
                }
                catch (Exception e2) {
                  e2.printStackTrace();
                }
              }

            }
          }
        }
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

            this.svg_panel = new com.kitfox.svg.app.beans.SVGPanel();
            //svg_panel.setSvgURI(svg);
            svg_panel.setSvgResourcePath("/simple_world_map.svg");
            svg_panel.setAutosize(com.kitfox.svg.app.beans.SVGPanel.AUTOSIZE_STRETCH);
            
            //svg_panel.setSize(200, 200);
            //svg_panel.setPreferredSize(new Dimension(200, 200));

            this.add(svg_panel, BorderLayout.CENTER);

            this.repaint();

            this.svg_uri = svg_panel.getSvgURI();

            // svgSalamander guarantees this reference will be the same that the GUI uses.
            this.svg_diagram = this.svg_universe.getDiagram(this.svg_uri);

          }
          catch (Exception e) {
            e.printStackTrace();
          }
      }

      public void actionPerformed(ActionEvent e) {
          System.out.println("JWAC_MapPanel actionPerformed e="+e);
          
      }

      public void setParentFrame(JWAC_MapFrame parentFrame) {
          this.parentFrame = parentFrame;
      }

      public JToolBar getToolBar() {
          return this.toolbar;
      }

      public void setDesignSpace(DesignSpace ds) {
        this.ds = ds;
        if (this.ds == null) {
          return;
        }

        // Grab all keys from all records, populate drop-down
        String[] all_property_names = new String[this.ds.getModel().getDimension()];
        for (int col_i=0; col_i < this.ds.getModel().getDimension(); col_i += 1) {
          all_property_names[col_i] = this.ds.getModel().getColumn(col_i).getTitle();
        }
        // Hack and a half to udpate in-place
        this.toolbar_map_value_selector.setModel(new JComboBox<String>(all_property_names).getModel());

        // Hack and a half to udpate in-place
        String[] constant_and_all_property_names = new String[1+all_property_names.length];
        constant_and_all_property_names[0] = "Constant (red)";
        for (int col_i=0; col_i < this.ds.getModel().getDimension(); col_i += 1) {
          constant_and_all_property_names[col_i + 1] = constant_and_all_property_names[col_i];
        }
        this.toolbar_map_color_field_selector.setModel(new JComboBox<String>(all_property_names).getModel());





      }



  }
}
