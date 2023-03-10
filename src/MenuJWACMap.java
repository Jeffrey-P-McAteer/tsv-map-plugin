
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import nddv.data.menu.ATSVMenuControls;
import nddv.data.session.SessionObjects;

import nddv.histogram.h1d.*; // TODO remove

public class MenuJWACMap extends ATSVMenuControls {
  public MenuJWACMap() {
    this.menuItem = new JMenuItem("JWAC Map Plots");
    this.menuItem.setMnemonic(72);
    this.menuItem.setEnabled(false);
    this.menuItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JWAC_MapFrame frame = new JWAC_MapFrame(SessionObjects.getDefaultDS());
            frame.setVisible(true);
          }
        });
    this.button = new JButton(new ImageIcon(getClass().getResource("/JWACMapPlot.gif")));
    this.button.setToolTipText("Add JWAC Map Plot");
    this.button.setEnabled(false);
    this.button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JWAC_MapFrame frame = new JWAC_MapFrame(SessionObjects.getDefaultDS());
            frame.setVisible(true);
          }
        });
  }
}
