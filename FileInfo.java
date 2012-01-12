package hc.main;

import java.io.File;
import java.io.IOException;

public class FileInfo {
	public String path; // 文件路径
	public String fileName; // 文件名
	public String type; // 文件类型
	public boolean checked; // 是否选中
	public int imageId; // 图片资源id
	public boolean directory;
	public File f;

	public FileInfo(File file, int imageId) {
		this.f = file;
		this.path = file.getPath();
		this.fileName = file.getName();
		this.type = type;
		this.directory = file.isDirectory();
		this.imageId = imageId;

	}

	public boolean isDirectory() {
		return directory;
	}

	public File getFile() {
		return f;
	}

	public String getPath() {

		return path;
	}
}
