
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
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

import  java.util.prefs.*;

import com.kitfox.svg.SVGElement;

public class JWAC_MapFrame extends JFrame implements Listener {
  private JWAC_MapPanel mapPanel;
  
  protected JButton brushSelection = new JButton(new ImageIcon(getClass().getResource("/images/linked.jpg")));
  
  private JPanel brushesPanel = new JPanel();
  
  private JPanel panelDisplay;

  private DesignSpace ds;

  public Preferences prefs;
  
  public JWAC_MapFrame(DesignSpace desSpace) {
    this.ds = desSpace;
    try {
      this.prefs = Preferences.userNodeForPackage(JWAC_MapFrame.class);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
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
  
  SVGElement last_picked_svg_element = null;

  public void pickOperation(String type) {
    //this.histogramPanel.brushUpdate();
    System.out.println("JWAC_MapFrame.pickOperation("+type+")");

    if (last_picked_svg_element != null) {
      // reset it
      try {
        last_picked_svg_element.setAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "red");
      }
      catch (Exception e) {
        //e.printStackTrace();
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

        String selected_country_name = this.ds.getColumn(column_i_to_use).getStringValue(selected_data_idx).trim();

        System.out.println("Highlighing the the country "+selected_country_name+"");
        System.out.println("diagram should be "+this.mapPanel.svg_universe.getDiagram(this.mapPanel.svg_panel.getSvgURI()));
        // this.svg_universe.getDiagram(svg_panel.getSvgURI());

        this.mapPanel.svg_diagram = com.kitfox.svg.SVGCache.getSVGUniverse().getDiagram(this.mapPanel.svg_panel.getSvgURI()); // Wierdness that fixes painting

        SVGElement element = this.mapPanel.svg_diagram.getElement(selected_country_name);
        if (element == null) {
          element = this.mapPanel.svg_diagram.getElement(selected_country_name.toLowerCase());
        }

        if (element != null) {
          System.out.println("Found "+selected_country_name+", element = "+element);
          try {
            element.setAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, "black");
          }
          catch (Exception e) {
            //e.printStackTrace();
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

  private HashMap<String, String> last_recolor_colors = new HashMap<String, String>();
  private HashMap<String, SVGElement> last_recolor_svg_paths = new HashMap<String, SVGElement>();

  public void recolor_all_countries() {
    try {
      this.mapPanel.svg_diagram = com.kitfox.svg.SVGCache.getSVGUniverse().getDiagram(this.mapPanel.svg_panel.getSvgURI()); // Wierdness that fixes painting

      // Get column index, or mark all red if == -1
      int color_column_i = this.mapPanel.toolbar_map_color_field_selector.getSelectedIndex() - 1 /* idx 0 == "Constant (red)", now it's == -1 */;
      if (color_column_i >= 0) {
        // For all data strings...
        
        MapColorValueCollisionStrategy row_collision_strat = (MapColorValueCollisionStrategy) this.mapPanel.toolbar_map_value_collision_strat.getSelectedItem();
        if (row_collision_strat == null) {
          System.out.println("WARNING: row_collision_strat == null, using average of all values for country!");
          row_collision_strat = MapColorValueCollisionStrategy.getAll().get(0);
        }

        //ArrayList<SVGElement> row_country_elements = new ArrayList<SVGElement>();
        //ArrayList<String> row_col_vals = new ArrayList<String>();
        
        int country_col_i_to_use = this.mapPanel.toolbar_map_value_selector.getSelectedIndex();
        int num_rows = this.ds.getColumn(country_col_i_to_use).size();

        HashMap<String, List<Double>> country_row_values = new HashMap<>();
        HashMap<String, SVGElement> country_svg_paths = new HashMap<>();

        int unparseable_values = 0;
        for (int row_i=0; row_i < num_rows; row_i += 1) {
          String row_country_name = this.ds.getColumn(country_col_i_to_use).getStringValue(row_i).trim();
          String row_col_val = this.ds.getColumn(color_column_i).getStringValue(row_i).trim();
          row_col_val = row_col_val.replace(",", "");
          
          if (!country_row_values.containsKey(row_country_name)) {
            country_row_values.put(row_country_name, new ArrayList<Double>());
          }
          try {
            country_row_values.get(row_country_name).add(
              Double.parseDouble(row_col_val)
            );
          }
          catch (Exception e) {
            //e.printStackTrace();
            unparseable_values += 1;
            //System.out.println("Coult not parse "+row_col_val+" to double!");
          }
          
          try {
            SVGElement row_country_elm = this.mapPanel.svg_diagram.getElement(row_country_name);
            if (row_country_elm == null) {
              row_country_elm = this.mapPanel.svg_diagram.getElement(row_country_name.toLowerCase());
            }
            if (row_country_elm != null) {
              country_svg_paths.put(row_country_name, row_country_elm);
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }

        if (unparseable_values > 0) {
          System.out.println("WARNING: for color column = "+color_column_i+" there were "+unparseable_values+" un-parseable row values!");
        }

        HashMap<String, Double> country_raw_values = new HashMap<>();
        for (String country_name : country_row_values.keySet()) {
          country_raw_values.put(country_name, row_collision_strat.apply(country_row_values.get(country_name)) );
        }
        Double max_country_raw_val = Double.NaN;
        Double min_country_raw_val = Double.NaN;
        for (String country_name : country_raw_values.keySet()) {
          if (max_country_raw_val.isNaN() || country_raw_values.get(country_name) > max_country_raw_val) {
            max_country_raw_val = country_raw_values.get(country_name);
          }
          if (min_country_raw_val.isNaN() || country_raw_values.get(country_name) < min_country_raw_val) {
            min_country_raw_val = country_raw_values.get(country_name);
          }
        }

        this.mapPanel.toolbar_map_value_min.setText(""+min_country_raw_val);
        this.mapPanel.toolbar_map_value_max.setText(""+max_country_raw_val);

        if (max_country_raw_val.isNaN() || min_country_raw_val.isNaN()) {
          System.out.println("No country row values could be parsed as numbers, not changing map color!");
          return;
        }
        if (max_country_raw_val == min_country_raw_val) {
          System.out.println("max and min values are identical ("+max_country_raw_val+", "+min_country_raw_val+"), not changing map color!");
          return;
        }

        System.out.println("max_country_raw_val = "+max_country_raw_val);
        System.out.println("min_country_raw_val = "+min_country_raw_val);

        float max_hue_val = 0.71f; // cannot wrap around, highest value is a blue color instead of red (confusing UI)

        // Normalize country_raw_values between 0 and 1.0 for color hue
        HashMap<String, Double> country_hue_values = new HashMap<>();
        for (String country_name : country_raw_values.keySet()) {
          country_hue_values.put(country_name,
            (country_raw_values.get(country_name) - min_country_raw_val) / ((max_country_raw_val - min_country_raw_val) * (1.0f/max_hue_val) )
          );
        }

        this.mapPanel.toolbar_map_value_min.setText(""+min_country_raw_val);
        this.mapPanel.toolbar_map_value_max.setText(""+max_country_raw_val);
        
        this.mapPanel.set_all_children_in_ds_to_bg("gray"); // set countries w/o a value to gray
        
        for (String country_name : country_hue_values.keySet()) {
          if (country_hue_values.containsKey(country_name)) {
            if (country_svg_paths.containsKey(country_name) && country_svg_paths.get(country_name) != null) {
              // Set color to HSV (hue val, 255, 255)
              //String fill_color_val = "hsl("+country_hue_values.get(country_name)+", 100%, 100%)";
              //System.out.println("recolor_all_countries "+country_name+" hue = "+country_hue_values.get(country_name)+" raw = "+country_raw_values.get(country_name));
              String fill_color_val = "#"+hsvToRgb((float) (double) country_hue_values.get(country_name), 1.0f, 1.0f);
              
              // Persist this info so selectors can restore old colors
              last_recolor_colors.put(country_name, fill_color_val);
              last_recolor_svg_paths.put(country_name, country_svg_paths.get(country_name) );

              try {
                country_svg_paths.get(country_name).setAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, fill_color_val);
              }
              catch (Exception e) {
                //e.printStackTrace();
                try {
                  country_svg_paths.get(country_name).addAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, fill_color_val);
                }
                catch (Exception e2) {
                  e2.printStackTrace();
                }
              }
            }
          }
        }

      }
      else {
        this.mapPanel.set_all_children_in_ds_to_red_bg();
      }

      this.mapPanel.svg_panel.repaint();
      this.mapPanel.repaint();
      
      // Finally color any selected element
      // last_picked_svg_element = null; // Do not reset a prior, we just colored them!
      // pickOperation("PointSelect");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String hsvToRgb(float hue, float saturation, float value) {

      int h = (int)(hue * 6);
      float f = hue * 6 - h;
      float p = value * (1 - saturation);
      float q = value * (1 - f * saturation);
      float t = value * (1 - (1 - f) * saturation);

      switch (h) {
        case 0: return rgbToString(value, t, p);
        case 1: return rgbToString(q, value, p);
        case 2: return rgbToString(p, value, t);
        case 3: return rgbToString(p, q, value);
        case 4: return rgbToString(t, p, value);
        case 5: return rgbToString(value, p, q);
        //default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        default: return rgbToString(hue + (hue < 0.5f? 0.01f : -0.01f ), saturation, value); // Seen default: taken w/ 1.0, 1.0, 1.0 inputs, move closer to 0.5 hue
      }
  }

  public static String rgbToString(float r, float g, float b) {
      String rs = String.format("%02X", (int)(r * 255));
      String gs = String.format("%02X", (int)(g * 255));
      String bs = String.format("%02X", (int)(b * 255));
      return rs + gs + bs;
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

      public JComboBox<MapColorValueCollisionStrategy> toolbar_map_value_collision_strat;

      public JLabel toolbar_map_value_min;
      public JLabel toolbar_map_value_max;

      public JWAC_MapPanel() {
          this.setLayout(new BorderLayout());

          this.toolbar_map_value_selector = new JComboBox<String>();
          this.toolbar_map_color_field_selector = new JComboBox<String>();
          this.toolbar_map_value_collision_strat = new JComboBox<MapColorValueCollisionStrategy>(
            MapColorValueCollisionStrategy.getAll().toArray( new MapColorValueCollisionStrategy[]{} )
          );
          
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
            // Also send current value to preferences API
            try {
              this.parentFrame.prefs.put("last_selected_col_name", ""+this.toolbar_map_value_selector.getSelectedItem() );
            }
            catch (Exception e) {
              e.printStackTrace();
            }
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

        JLabel color_field_label = new JLabel("Color Field");
        toolbar.add(flowLeftWrapper(color_field_label));

        this.toolbar_map_color_field_selector.setMaximumRowCount(18); // show lots of rows
        this.toolbar_map_color_field_selector.addActionListener((evt) -> {
          if (this.parentFrame != null) {
            this.parentFrame.recolor_all_countries();
          }
        });
        toolbar.add(this.toolbar_map_color_field_selector);

        JLabel color_field_collision_label = new JLabel("Color Field value collision stragety");
        toolbar.add(flowLeftWrapper(color_field_collision_label));

        this.toolbar_map_value_collision_strat.setMaximumRowCount(18); // show lots of rows
        this.toolbar_map_value_collision_strat.addActionListener((evt) -> {
          if (this.parentFrame != null) {
            this.parentFrame.recolor_all_countries();
          }
        });
        toolbar.add(this.toolbar_map_value_collision_strat);

        this.toolbar_map_value_min = new JLabel("0");
        this.toolbar_map_value_max = new JLabel("1");
        
        JPanel toolbar_map_values = new JPanel();
        toolbar_map_values.setLayout(new BorderLayout());
        toolbar_map_values.add(this.toolbar_map_value_min, "West");
        toolbar_map_values.add(this.toolbar_map_value_max, "East");
        toolbar.add(toolbar_map_values);

        try {
          // JLabel color_icon_label = new JLabel();
          // color_icon_label.setIcon(
          //   new javax.swing.ImageIcon(
          //     //new javax.swing.ImageIcon(getClass().getResource("/images/gradientbluered.png")) // image from atsv.jar
          //     new javax.swing.ImageIcon(getClass().getResource("/images/gradientredblue.png"))
          //       .getImage().getScaledInstance(200, 20, java.awt.Image.SCALE_SMOOTH)
          //   )
          // );
          // color_icon_label.setIconTextGap(0);
          // color_icon_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
          // color_icon_label.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
          
          final java.awt.Image image = new javax.swing.ImageIcon(getClass().getResource("/images/gradientredblue.png"))
                .getImage().getScaledInstance(200, 20, java.awt.Image.SCALE_SMOOTH);

          JPanel img_panel = new JPanel() {
            @Override
            public void paint(java.awt.Graphics g) {
              g.drawImage(image, 0, 0, null);
            }
          };
          toolbar.add(img_panel);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        
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
            SVGElement child = parent.getChild(i);
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
        set_all_children_in_ds_to_bg(null, "red");
      }

      private void set_all_children_in_ds_to_bg(String fill_val) {
        set_all_children_in_ds_to_bg(null, fill_val);
      }

      private void set_all_children_in_ds_to_bg(com.kitfox.svg.Group parent, String fill_val) {
        if (this.svg_diagram != null) {
          if (parent == null) {
            parent = this.svg_diagram.getRoot();
          }
          for (int i=0; i<parent.getNumChildren(); i+=1) {
            SVGElement child = parent.getChild(i);
            if (child instanceof com.kitfox.svg.Group) {
              set_all_children_in_ds_to_bg((com.kitfox.svg.Group) child, fill_val);
            }
            else {

              try {
                child.setAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, fill_val);
              }
              catch (Exception e) {
                //e.printStackTrace();
                try {
                  child.addAttribute("fill", com.kitfox.svg.animation.AnimationElement.AT_CSS, fill_val);
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
            this.addMouseListener(new MouseAdapter() {
              public void mouseClicked(MouseEvent evt) {
                JWAC_MapPanel.this.onMouseClicked(evt);
              }
            });
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

      private static HashMap<String, nddv.gui.DetailWindow> details_cache = new HashMap<>();

      public void onMouseClicked(java.awt.event.MouseEvent evt) {
        // System.out.println("JWAC_MapPanel onMouseClicked evt="+evt);
        try {
          if (evt.getClickCount() <= 1) { // ignore single-click events
            return;
          }

          this.svg_diagram = com.kitfox.svg.SVGCache.getSVGUniverse().getDiagram(this.svg_panel.getSvgURI());

          // evt.getPoint() is in screen coords, must convert to svg coords
          float width_scale = this.svg_diagram.getWidth() / ((float) this.svg_panel.getSize().width);
          float height_scale = this.svg_diagram.getHeight() / ((float) this.svg_panel.getSize().height);
          java.awt.Point svg_point = new java.awt.Point(
            (int) (width_scale * (float) evt.getPoint().x),
            (int) (height_scale * (float) evt.getPoint().y)
          );

          List<List<SVGElement>> clicked_elements = this.svg_diagram.pick(
            svg_point, null
          );

          ArrayList<String> ids_clicked = new ArrayList<>();
          for (List<SVGElement> elms : clicked_elements) {
            for (SVGElement elm : elms) {
              ids_clicked.add( elm.getId().trim() );
            }
          }

          int country_col_i_to_use = this.toolbar_map_value_selector.getSelectedIndex();
          int num_rows = this.ds.getColumn(country_col_i_to_use).size();

          boolean opened_details = false;

          System.out.println("");
          for (String svg_elm_id : ids_clicked) {
            System.out.println("User double-clicked graph svg id path = "+svg_elm_id);
            // Lookup all rows with this ID as sountry and open them

            int unparseable_values = 0;
            for (int row_i=0; row_i < num_rows; row_i += 1) {
              String row_country_name = this.ds.getColumn(country_col_i_to_use).getStringValue(row_i).trim();
              if (row_country_name.equalsIgnoreCase(svg_elm_id)) {
                
                // row matches, open row_i for details!
                
                try {
                  String details_win_cache_key = row_country_name+"_"+row_i;
                  if (!details_cache.containsKey(details_win_cache_key)) {
                    nddv.gui.DetailWindow sdw = new nddv.gui.DetailWindow(this.ds, row_i);
                    details_cache.put(details_win_cache_key, sdw);
                  }
                  details_cache.get(details_win_cache_key).setVisible(true);
                  details_cache.get(details_win_cache_key).requestFocus();
                  opened_details = true;
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
                
              }
            }
          }

          if (ids_clicked.size() > 1 && !opened_details) {
            System.out.println("No matching countries, is column "+country_col_i_to_use+" the one with country names?");
          }


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

        try {
          // Attempt to use java preferences API to read back the last-picked value for this
          String last_selected_col_name = this.parentFrame.prefs.get("last_selected_col_name", "");
          if (last_selected_col_name != null && last_selected_col_name.length() > 0) {
            this.toolbar_map_value_selector.setSelectedItem(last_selected_col_name);
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }

        // Hack and a half to udpate in-place
        String[] constant_and_all_property_names = new String[1+all_property_names.length];
        constant_and_all_property_names[0] = "Constant (red)";
        for (int col_i=0; col_i < this.ds.getModel().getDimension(); col_i += 1) {
          constant_and_all_property_names[col_i + 1] = all_property_names[col_i];
        }
        this.toolbar_map_color_field_selector.setModel(new JComboBox<String>(constant_and_all_property_names).getModel());


      }



  }


  // Responsible for encoding strategies of converting [N] floats into a single value N,
  // for example average(1,2,3) -> 2, first(1,2,3) -> 1, sum(1,2,3) -> 6 etc.
  public static class MapColorValueCollisionStrategy {

    private String name;
    private Function<List<Double>, Double> strategy;
    public MapColorValueCollisionStrategy(String name, Function<List<Double>, Double> strategy) {
      this.name = name;
      this.strategy = strategy;
    }
    public Double apply(List<Double> values) {
      return this.strategy.apply(values);
    }

    @Override
    public String toString() {
      return this.name;
    }

    public static ArrayList<MapColorValueCollisionStrategy> getAll() {
      ArrayList<MapColorValueCollisionStrategy> all = new ArrayList<MapColorValueCollisionStrategy>(16);
      
      all.add(new MapColorValueCollisionStrategy("average of all values for country", (numbers) -> {
        double sum = 0.0;
        for (double d : numbers) {
          sum += d;
        }
        return sum / (double) numbers.size();
      }));

      all.add(new MapColorValueCollisionStrategy("sum all values for country", (numbers) -> {
        double sum = 0.0;
        for (double d : numbers) {
          sum += d;
        }
        return sum;
      }));

      all.add(new MapColorValueCollisionStrategy("first of all values for country", (numbers) -> {
        double val = 0.0;
        for (double d : numbers) {
          val = d;
          break;
        }
        return val;
      }));

      return all;
    }

  }
}
