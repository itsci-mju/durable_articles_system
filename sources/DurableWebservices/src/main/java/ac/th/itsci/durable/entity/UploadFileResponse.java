package ac.th.itsci.durable.entity;

public class UploadFileResponse {
	
	private String fileName;
    private String fileImageUrl;
    private String fileType;
    private long size;
    
	public UploadFileResponse(String fileName, String fileImageUrl, String fileType,
			long size) {
		super();
		this.fileName = fileName;
		this.fileImageUrl = fileImageUrl;
		this.fileType = fileType;
		this.size = size;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileImageUrl() {
		return fileImageUrl;
	}
	public void setFileImageUrl(String fileImageUrl) {
		this.fileImageUrl = fileImageUrl;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
