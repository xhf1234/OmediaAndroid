
package org.tsinghua.omedia.ui.fileBrowser;

import java.io.File;

/**
 * @author carelife
 */
public class FileInfoDataSet {
    private String path; // 文件路径

    private String fileName; // 文件名

    private String type; // 文件类型

    private boolean checked; // 是否选中

    private int imageId; // 图片资源id

    private boolean directory;

    private File f;

    public FileInfoDataSet(File file, String type) {
        this.f = file;
        this.path = file.getPath();
        this.fileName = file.getName();
        this.type = type;
        this.directory = file.isDirectory();
    }

    public FileInfoDataSet(String fileName, int imageId) {
        this.fileName = fileName;
        this.imageId = imageId;

    }

    public FileInfoDataSet(File file, int imageId) {
        this.f = file;
        this.path = file.getPath();
        this.fileName = file.getName();
        this.directory = file.isDirectory();
        this.imageId = imageId;

    }

    /**
     * This is for virtual directory
     * @param fileName
     * @param imageId
     * @param directory
     */
    public FileInfoDataSet(String fileName, int imageId, boolean directory) {
        this.directory = directory;
        this.imageId = imageId;
        this.fileName = fileName;
    }

    public boolean getIsDirectory() {
        return directory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.directory = isDirectory;
    }

    public void setFile(File f) {
        this.f = f;
    }

    public File getFile() {
        return f;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTyep() {
        return type;
    }

    public void setIsChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getIsChecked() {
        return checked;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
