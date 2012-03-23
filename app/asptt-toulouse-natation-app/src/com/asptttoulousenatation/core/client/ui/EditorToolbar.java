package com.asptttoulousenatation.core.client.ui;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.axeiya.gwtckeditor.client.ToolbarLine;

public class EditorToolbar extends CKConfig {

	public EditorToolbar() {
		super();
		buildToolbar();
	}
	
	private void buildToolbar() {
		Toolbar lToolbar = new Toolbar();
		
		//Apercu
		ToolbarLine lToolbarLine = new ToolbarLine();
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Preview);
		lToolbarLine.addBlockSeparator();
		
		//Copy paste...
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Cut);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Copy);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Paste);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.PasteText);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.PasteFromWord);
		lToolbarLine.addBlockSeparator();
		
		//Print, orthographe, orthographe options
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Print);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.SpellChecker);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Scayt);
		lToolbarLine.addBlockSeparator();
		
		//Undo, redo
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Undo);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Redo);
		lToolbarLine.addBlockSeparator();
		
		//Find, replace
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Find);
		lToolbarLine.add(CKConfig.TOOLBAR_OPTIONS.Replace);
		lToolbarLine.addBlockSeparator();
		
		ToolbarLine lToolbarLine2 = new ToolbarLine();
		
		//Select all, suppress format
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.SelectAll);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.RemoveFormat);
		lToolbarLine2.addBlockSeparator();
		
		//Bold, Italic, Underline, Strike, Quote
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Bold);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Italic);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Underline);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Strike);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Blockquote);
		lToolbarLine2.addBlockSeparator();
		
		//Lists (number, bullet)
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.NumberedList);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.BulletedList);
		lToolbarLine2.addBlockSeparator();
		
		//Out-In dent
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Outdent);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Indent);
		lToolbarLine2.addBlockSeparator();
		
		//Align
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.JustifyLeft);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.JustifyCenter);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.JustifyRight);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.JustifyBlock);
		lToolbarLine2.addBlockSeparator();
		
		//Link, anchor
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Link);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Unlink);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Anchor);
		lToolbarLine2.addBlockSeparator();
		
		//Image, Flash, Table, Horizontal line, smiley, special characters, page break
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Image);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Flash);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Table);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.HorizontalRule);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.Smiley);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.SpecialChar);
		lToolbarLine2.add(CKConfig.TOOLBAR_OPTIONS.PageBreak);
		lToolbarLine2.addBlockSeparator();
		
		ToolbarLine lToolbarLine3 = new ToolbarLine();
		//style
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.Styles);
		lToolbarLine3.addBlockSeparator();
		
		//Paragraph
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.Format);
		lToolbarLine3.addBlockSeparator();
		
		//Font
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.Font);
		lToolbarLine3.addBlockSeparator();
		
		//Size
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.FontSize);
		lToolbarLine3.addBlockSeparator();
		
		//Colors
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.TextColor);
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.BGColor);
		lToolbarLine3.addBlockSeparator();
		
		//Maximize, select block
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.Maximize);
		lToolbarLine3.add(CKConfig.TOOLBAR_OPTIONS.ShowBlocks);
		lToolbarLine3.addBlockSeparator();
		lToolbar.add(lToolbarLine);
		lToolbar.addSeparator();
		lToolbar.add(lToolbarLine2);
		lToolbar.addSeparator();
		lToolbar.add(lToolbarLine3);
		setToolbar(lToolbar);
	}

}
