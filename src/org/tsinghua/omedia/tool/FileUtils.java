
package org.tsinghua.omedia.tool;

import java.io.File;
import java.util.ArrayList;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.ui.fileBrowser.FileInfoDataSet;

public class FileUtils {

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * 
     * @param file
     */
    public static String getMIMEType(File file) {
        String fName = file.getName();
        return getMIMEType(fName);
    }

    public static String getMIMEType(String fName) {
        String type = "*/*";
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static int getImageIdByType(String type) {
        if (type.equals("audio/x-mpegurl") || type.equals("audio/mp4a-latm")
                || type.equals("audio/x-mpeg") || type.equals("audio/x-wav")
                || type.equals("audio/ogg") || type.equals("audio/x-ms-wma"))
            return R.drawable.filebrower_mp3;

        if (type.equals("video/3gpp") || type.equals("video/x-ms-asf")
                || type.equals("video/x-msvideo") || type.equals("video/vnd.mpegurl")
                || type.equals("video/x-ms-wmv") || type.equals("video/x-pn-realaudio")
                || type.equals("video/quicktime") || type.equals("video/x-m4v")
                || type.equals("video/mp4") || type.equals("video/mpeg"))
            return R.drawable.filebrower_midea;

        if (type.equals("application/x-tar") || type.equals("application/x-compressed")
                || type.equals("application/zip") || type.equals("application/x-compress")
                || type.equals("application/x-gzip") || type.equals("application/x-gtar")
                || type.equals("application/java-archive"))
            return R.drawable.filebrower_zip;

        if (type.equals("application/msword"))
            return R.drawable.filebrower_office;

        return R.drawable.filebrower_file;
    }

    public static ArrayList<FileInfoDataSet> getCurrentDirFiles(File file) {
        File[] currentFiles = file.listFiles();
        ArrayList<FileInfoDataSet> array = new ArrayList<FileInfoDataSet>();
        FileInfoDataSet info;

        for (File f : currentFiles) {
            if (!f.canRead())
                continue;
            if (f.isFile()) {
                info = new FileInfoDataSet(f, FileUtils.getImageIdByType(FileUtils.getMIMEType(f
                        .getName())));
                array.add(info);
            } else {
                info = new FileInfoDataSet(f, R.drawable.filebrower_folder);
                array.add(info);
            }
        }
        return array;
    }

    private final static String[][] MIME_MapTable = {
            // {后缀名， MIME类型}
            {
                    ".3gp", "video/3gpp"
            }, {
                    ".apk", "application/vnd.android.package-archive"
            }, {
                    ".asf", "video/x-ms-asf"
            }, {
                    ".avi", "video/x-msvideo"
            }, {
                    ".bin", "application/octet-stream"
            }, {
                    ".bmp", "image/bmp"
            }, {
                    ".c", "text/plain"
            }, {
                    ".class", "application/octet-stream"
            }, {
                    ".conf", "text/plain"
            }, {
                    ".cpp", "text/plain"
            }, {
                    ".doc", "application/msword"
            }, {
                    ".exe", "application/octet-stream"
            }, {
                    ".gif", "image/gif"
            }, {
                    ".gtar", "application/x-gtar"
            }, {
                    ".gz", "application/x-gzip"
            }, {
                    ".h", "text/plain"
            }, {
                    ".htm", "text/html"
            }, {
                    ".html", "text/html"
            }, {
                    ".jar", "application/java-archive"
            }, {
                    ".java", "text/plain"
            }, {
                    ".jpeg", "image/jpeg"
            }, {
                    ".jpg", "image/jpeg"
            }, {
                    ".js", "application/x-javascript"
            }, {
                    ".log", "text/plain"
            }, {
                    ".m3u", "audio/x-mpegurl"
            }, {
                    ".m4a", "audio/mp4a-latm"
            }, {
                    ".m4b", "audio/mp4a-latm"
            }, {
                    ".m4p", "audio/mp4a-latm"
            }, {
                    ".m4u", "video/vnd.mpegurl"
            }, {
                    ".m4v", "video/x-m4v"
            }, {
                    ".mov", "video/quicktime"
            }, {
                    ".mp2", "audio/x-mpeg"
            }, {
                    ".mp3", "audio/x-mpeg"
            }, {
                    ".mp4", "video/mp4"
            }, {
                    ".mpc", "application/vnd.mpohun.certificate"
            }, {
                    ".mpe", "video/mpeg"
            }, {
                    ".mpeg", "video/mpeg"
            }, {
                    ".mpg", "video/mpeg"
            }, {
                    ".mpg4", "video/mp4"
            }, {
                    ".mpga", "audio/mpeg"
            }, {
                    ".msg", "application/vnd.ms-outlook"
            }, {
                    ".ogg", "audio/ogg"
            }, {
                    ".pdf", "application/pdf"
            }, {
                    ".png", "image/png"
            }, {
                    ".pps", "application/vnd.ms-powerpoint"
            }, {
                    ".ppt", "application/vnd.ms-powerpoint"
            }, {
                    ".prop", "text/plain"
            }, {
                    ".rar", "application/x-rar-compressed"
            }, {
                    ".rc", "text/plain"
            }, {
                    ".rmvb", "video/x-pn-realaudio"
            }, {
                    ".rtf", "application/rtf"
            }, {
                    ".sh", "text/plain"
            }, {
                    ".tar", "application/x-tar"
            }, {
                    ".tgz", "application/x-compressed"
            }, {
                    ".txt", "text/plain"
            }, {
                    ".wav", "audio/x-wav"
            }, {
                    ".wma", "audio/x-ms-wma"
            }, {
                    ".wmv", "video/x-ms-wmv"
            }, {
                    ".wps", "application/vnd.ms-works"
            },
            // {".xml", "text/xml"},
            {
                    ".xml", "text/plain"
            }, {
                    ".z", "application/x-compress"
            }, {
                    ".zip", "application/zip"
            }, {
                    "", "*/*"
            }
    };
}
