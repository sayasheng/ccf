package org.ccf.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import com.hexapixel.widgets.ribbon.ButtonSelectGroup;
import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonCheckbox;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroupSeparator;
import com.hexapixel.widgets.ribbon.RibbonShell;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;
import com.hexapixel.widgets.ribbon.RibbonToolbar;
import com.hexapixel.widgets.ribbon.RibbonToolbarGrouping;
import com.hexapixel.widgets.ribbon.RibbonTooltip;
import com.hexapixel.widgets.generic.ImageCache;
import com.hexapixel.widgets.generic.Utils;

public class MainUI {
	public static void main(String args []) {
		Display display = new Display();
		final RibbonShell shell = new RibbonShell(display);
        shell.setButtonImage(ImageCache.getImage("selection_recycle_24.png"));
        //Shell shell = new Shell(display);
        
        shell.setText("�i�R����Ʈw�޲z�t��");
        shell.setSize(1024, 600);
        Text text = new Text(shell.getShell(), SWT.BORDER);
        /*
        QuickAccessShellToolbar mtb = shell.getToolbar();
        RibbonButton mtbtb1 = new RibbonButton(mtb, ImageCache.getImage("gear_ok_16.gif"), null, SWT.NONE);
        RibbonButton mtbtb2 = new RibbonButton(mtb, ImageCache.getImage("gantt_16.gif"), null, SWT.NONE);
        shell.setBigButtonTooltip(new RibbonTooltip("Big", "I'm the tooltip for the big button"));
        mtb.setArrowTooltip(new RibbonTooltip("Oh", "Jeez"));
        
        Menu shellMenu = shell.getBigButtonMenu();
        MenuItem btest = new MenuItem(shellMenu, SWT.POP_UP);
        btest.setText("Testing a menu");
        */
        
        shell.addBigButtonListener(new SelectionListener() {

                public void widgetDefaultSelected(SelectionEvent e) {
                }

                public void widgetSelected(SelectionEvent e) {
                        System.err.println("Clicked big button");
                        shell.showBigButtonMenu();
                }
                
        });
        
        
        //shell.setLayout(new FillLayout());
        //Composite inner = new Composite(shell, SWT.None);
        //inner.setLayout(new FillLayout(SWT.VERTICAL)); 
        //inner.setBackground(ColorCache.getInstance().getColor(182, 206, 238));                
        
        // Tab folder
        //final RibbonTabFolder ftf = new RibbonTabFolder(inner, SWT.NONE);
        RibbonTabFolder ftf = shell.getRibbonTabFolder();
        ftf.setHelpImage(ImageCache.getImage("questionmark.gif"));
        ftf.getHelpButton().setToolTip(new RibbonTooltip("Title", "Get Help Using Whatever This Is"));
        
        //ftf.setDrawEmptyTabs(false);
        // Tabs
        RibbonTab servicehour = new RibbonTab(ftf, "�ɼƲέp");
        RibbonTab monthReport = new RibbonTab(ftf, "��T");
        RibbonTab activity = new RibbonTab(ftf, "���ʸ�T");
        RibbonTab input = new RibbonTab(ftf, "��J���");               
        //new RibbonTab(ftf, "Empty");
        
        // Tooltip
        RibbonTooltip toolTip = new RibbonTooltip("Some Action Title", "This is content text that\nsplits over\nmore than one\nline\n\\b\\c255000000and \\xhas \\bdifferent \\c000000200look \\xand \\bfeel.", ImageCache.getImage("tooltip.jpg"), ImageCache.getImage("questionmark.gif"), "Press F1 for more help"); 

        // ServiceHour Group
        
        // toolbar group
        /*
        RibbonGroup tbGroup = new RibbonGroup(servicehour, "Toolbar Here");
        RibbonToolbar toolbar = new RibbonToolbar(tbGroup, RibbonToolbar.STYLE_BORDERED, 2);
        RibbonToolbarGrouping rtg = new RibbonToolbarGrouping(toolbar, 1);              
        RibbonToolbarGrouping rtg2 = new RibbonToolbarGrouping(toolbar, 1);
        RibbonToolbarGrouping rtg3 = new RibbonToolbarGrouping(toolbar, 1);
        RibbonToolbarGrouping rtg4 = new RibbonToolbarGrouping(toolbar, 2);
        
        RibbonButton rbTb1 = new RibbonButton(rtg, ImageCache.getImage("books_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN_SPLIT | RibbonButton.STYLE_TOGGLE);
        RibbonButton rbTb2 = new RibbonButton(rtg2, ImageCache.getImage("gear_ok_16.gif"), null, SWT.NONE);
        RibbonButton rbTb3 = new RibbonButton(rtg2, ImageCache.getImage("gantt_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN);
        RibbonButton rbTb4 = new RibbonButton(rtg3, ImageCache.getImage("gantt_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN_SPLIT);
        RibbonButton rbTb5 = new RibbonButton(rtg4, ImageCache.getImage("enabled_small.gif"), null, RibbonButton.STYLE_NO_DEPRESS);
        RibbonButton rbTb6 = new RibbonButton(rtg4, ImageCache.getImage("selection_recycle_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN_SPLIT);
        
        rbTb4.setEnabled(false);
        // end toolbar group
        */
        RibbonGroup ftg = new RibbonGroup(servicehour, "�U���ɼ�", toolTip);
        // Button
        RibbonButton hours_noMeeting = new RibbonButton(ftg, ImageCache.getImage("clock.png"),"�A�Ȯɼ�",RibbonButton.STYLE_NO_DEPRESS);
        hours_noMeeting.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}

			@Override
			public void widgetSelected(SelectionEvent e) {
				 System.out.println("service hours, meeting not included");
				
			}
        	
        });
        
        RibbonButton brochure_hours = new RibbonButton(ftg, ImageCache.getImage("books.png"),"�Ӥu��U�ɼ�",RibbonButton.STYLE_NO_DEPRESS);
        brochure_hours.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}

			@Override
			public void widgetSelected(SelectionEvent e) {
				 System.out.println("brochure hours");
				
			}
        	
        });
        
        RibbonButton all_hours = new RibbonButton(ftg, ImageCache.getImage("Calendar_Month.png"),"�Ҧ����ʮɼ�",RibbonButton.STYLE_NO_DEPRESS);
        brochure_hours.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}

			@Override
			public void widgetSelected(SelectionEvent e) {
				 System.out.println("all hours");
				
			}
        	
        });
        /*
        RibbonButton rb = new RibbonButton(ftg, ImageCache.getImage("olb_picture.gif"), "I have two\nrows", RibbonButton.STYLE_TWO_LINE_TEXT | RibbonButton.STYLE_ARROW_DOWN);//RibbonButton.STYLE_ARROW_DOWN_SPLIT);
        RibbonButton rb2 = new RibbonButton(ftg, ImageCache.getImage("olb_picture.gif"), "I'm split\ntoggle", RibbonButton.STYLE_ARROW_DOWN_SPLIT | RibbonButton.STYLE_TOGGLE | RibbonButton.STYLE_TWO_LINE_TEXT);
        rb2.setBottomOrRightToolTip(toolTip);
        MenuItem test = new MenuItem(rb2.getMenu(), SWT.POP_UP);
        test.setText("Testing a menu");

        rb2.addSelectionListener(new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                        
                }

                public void widgetSelected(SelectionEvent e) {
                        final RibbonButton rb = (RibbonButton) e.data;
                        if (rb.isTopSelected()) 
                                System.err.println("Top clicked");
                        else {
                                System.err.println("Bottom clicked");
                                rb.showMenu();                                  
                        }
                }                       
        });
        // Empty group
        RibbonGroup cb = new RibbonGroup(input, "Checkboxes", toolTip);
        RibbonButtonGroup cbg = new RibbonButtonGroup(cb);
        
        RibbonCheckbox rc = new RibbonCheckbox(cbg, "I'm checked", SWT.NONE);
        rc.setSelected(true);
        new RibbonCheckbox(cbg, "I'm not", SWT.NONE);
        RibbonCheckbox rc3 = new RibbonCheckbox(cbg, "I'm disabled", SWT.NONE);
        new RibbonCheckbox(cbg, "Also a checkbox", SWT.NONE);
        new RibbonCheckbox(cbg, "Many, aren't we?", SWT.NONE);
        rc3.setEnabled(false);
        RibbonTooltip cbTip = new RibbonTooltip("Title", "Checkbox description\n\\b\\c255000000Some bold and red \\xand\nSome not!", null, ImageCache.getImage("questionmark.gif"), "Press F1 for more help");
        rc3.setToolTip(cbTip);

        rb.setToolTip(toolTip);
        
        //TODO: Check when a dialog opens as a result of clicking this to see if this button does not redraw for some reason or think it's still selected
        new RibbonButton(ftg, ImageCache.getImage("olb_picture.gif"), "I am longer and do not depress", RibbonButton.STYLE_NO_DEPRESS);

        RibbonGroup ftg2 = new RibbonGroup(input, "Group 1");
        RibbonButton rb1 = new RibbonButton(ftg2, ImageCache.getImage("olb_picture2.gif"), "Button 1", SWT.NONE);
        //RibbonButton rb2 = new RibbonButton(ftg2, ImageCache.getImage("olb_picture3.gif"), "Button 2", SWT.NONE);

        RibbonGroup ftg3 = new RibbonGroup(input, "Group 2");
        RibbonButton rb3 = new RibbonButton(ftg3, ImageCache.getImage("olb_picture4.gif"), "Button 3", SWT.NONE);
        RibbonButton rb4 = new RibbonButton(ftg3, ImageCache.getImage("olb_picture6.gif"), "Button 4", SWT.NONE);
        rb4.setToolTip(toolTip);

        ButtonSelectGroup group = new ButtonSelectGroup();
        
        // native controls example
        RibbonGroup ftg4 = new RibbonGroup(input, "Native");
        GridLayout gl = new GridLayout(1, false);
        gl.marginHeight = 7;
        gl.marginLeft = 0;
        gl.marginRight = 0;
        gl.verticalSpacing = 1;
        gl.horizontalSpacing = 0;
        gl.marginBottom = 7;
        ftg4.setLayout(gl);
        Combo foo = new Combo(ftg4, SWT.READ_ONLY);
        foo.setText("Testing");
        foo.add("Testing 2");
        foo.add("Testing 3");
        foo.add("Testing 4");
        Button b = new Button(ftg4, SWT.PUSH);
        b.setText("Test");
                        
        // create sub button containing 3 buttons inside it
        new RibbonGroupSeparator(ftg);
        
        RibbonButtonGroup sub = new RibbonButtonGroup(ftg);
        RibbonButton sub1 = new RibbonButton(sub, ImageCache.getImage("enabled_small.gif"), ImageCache.getImage("disabled_small.gif"), "Disabled", SWT.NONE);
        sub1.setEnabled(false);
        new RibbonCheckbox(sub, "I'm mixed in", SWT.NONE);

        // make arrow down
        RibbonButton rb5 = new RibbonButton(sub, ImageCache.getImage("olb_small2.gif"), "I am toggle split", RibbonButton.STYLE_TOGGLE | RibbonButton.STYLE_ARROW_DOWN_SPLIT);
        RibbonButton rb6 = new RibbonButton(sub, ImageCache.getImage("olb_small3.gif"), "I am a quite long button", SWT.NONE);
        RibbonButton rb7 = new RibbonButton(sub, ImageCache.getImage("olb_small3.gif"), "I split normal", RibbonButton.STYLE_ARROW_DOWN_SPLIT);
        RibbonButton rb8 = new RibbonButton(sub, ImageCache.getImage("olb_small3.gif"), "I am arrowed", RibbonButton.STYLE_ARROW_DOWN);

        MenuItem test2 = new MenuItem(rb8.getMenu(), SWT.POP_UP);
        test2.setText("Testing an arrow down menu");

        rb8.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(SelectionEvent e) {
                }

                public void widgetSelected(SelectionEvent e) {
                        final RibbonButton rb = (RibbonButton) e.data;
                        rb.showMenu();                                  
                }
                
        });
        
        rb1.setButtonSelectGroup(group);
        rb2.setButtonSelectGroup(group);
        rb3.setButtonSelectGroup(group);
        rb4.setButtonSelectGroup(group);
        rb5.setButtonSelectGroup(group);
        rb6.setButtonSelectGroup(group);
                                */
        Utils.centerDialogOnScreen(shell.getShell());

        shell.open();
        
        while (!shell.isDisposed ()) {
                if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
}

	
}
