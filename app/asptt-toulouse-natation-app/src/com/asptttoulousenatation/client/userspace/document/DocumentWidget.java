package com.asptttoulousenatation.client.userspace.document;

import static com.asptttoulousenatation.client.Asptt_toulouse_natation_app.CSS;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.Uploader;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DocumentWidget extends Composite {

	private VerticalPanel panel;
	private TextBox title;
	private TextBox summary;
	private SingleUploader defaultUploader = new SingleUploader();
	
	public DocumentWidget(final Long pMenuId) {
		panel = new VerticalPanel();
		initWidget(panel);
		FlexTable lPanel = new FlexTable();
		Label lTitle = new Label("Intitulé");
		lPanel.setWidget(0, 0, lTitle);
		title = new TextBox();
		title.setName("fileTitle");
		lPanel.setWidget(0, 1, title);
		
		Label lSummary = new Label("Description");
		lPanel.setWidget(1, 0, lSummary);
		summary = new TextBox();
		summary.setName("fileSummary");
		lPanel.setWidget(1, 1, summary);
		
		lPanel.setWidget(2, 0, defaultUploader);
		FlexCellFormatter lCellFormatter = lPanel.getFlexCellFormatter();
		lCellFormatter.setColSpan(2, 0, 2);
		
		panel.add(lPanel);
		defaultUploader.addOnStartUploadHandler(new IUploader.OnStartUploaderHandler() {
			
			public void onStart(IUploader pUploader) {
				String lUrl = pUploader.getServletPath() + "?fileTitle=" + title.getValue() + "&fileSummary=" + summary.getValue() + "&menuId=" + pMenuId;
				pUploader.setServletPath(lUrl);
			}
		});
		defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
		
		panel.setStyleName(CSS.documentWidget());
	}
	
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
	    public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {
	    	  if (uploader.getStatus() == Status.SUCCESS) {
	    	        String url = uploader.getServletPath() + "?blob-key=" + uploader.getServerInfo().message;
	    	        System.out.println(url);
	    	        UploadedInfo info = uploader.getServerInfo();
	    	        System.out.println("File name " + info.name);
	    	        System.out.println("File content-type " + info.ctype);
	    	        System.out.println("File size " + info.size);

	        // You can send any customized message and parse it 
	        System.out.println("Server message " + info.message);
	    	  }
	      }
	    }
	  };
	  
	  public class MyMultiuploader extends MultiUploader {
		  private boolean autoSubmit = false;

		  public boolean getAutoSubmit() {
		    return autoSubmit;
		  }

		  public void setAutoSubmit(boolean autoSubmit) {
		    this.autoSubmit = autoSubmit;
		  }
		  
		  @Override
		  protected IUploader getUploaderInstance() {
		    return new Uploader(fileInputType, autoSubmit);
		  }
		  
		  @Override
		  public void newUploaderInstance() {
		    super.newUploaderInstance();
		  }

		}
}
