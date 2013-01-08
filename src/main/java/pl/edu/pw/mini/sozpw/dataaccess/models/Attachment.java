package pl.edu.pw.mini.sozpw.dataaccess.models;

public class Attachment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public Integer attachmentId;
	public String filename;
	public String fileType;
	public Integer fileSize;
	public byte[] File;
	public Integer note_id;
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public byte[] getFile() {
		return File;
	}
	public void setFile(byte[] file) {
		File = file;
	}
	public Integer getNote_id() {
		return note_id;
	}
	public void setNote_id(Integer note_id) {
		this.note_id = note_id;
	}
}
