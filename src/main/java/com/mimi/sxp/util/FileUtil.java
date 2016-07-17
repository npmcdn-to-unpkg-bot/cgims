package com.mimi.sxp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	private static final int MAX_BUFFER_SIZE = 4 * 1024 * 1024;
	private static final int MIN_BUFFER_SIZE = 1024;
	private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

	public static void copy(InputStream ins, OutputStream outs, int size)
			throws IOException {
		size = Math.min(size, MAX_BUFFER_SIZE);
		size = Math.max(size, MIN_BUFFER_SIZE);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		byte[] buf = new byte[size];
		int length;
		try {
			bis = new BufferedInputStream(ins, size);
			bos = new BufferedOutputStream(outs, size);
			while ((length = bis.read(buf)) != -1) {
				bos.write(buf, 0, length);
			}
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.flush();
				bos.close();
			}
			if (ins != null) {
				ins.close();
			}
			if (outs != null) {
				outs.close();
			}
		}
	}

	public static void copy(File inf, File outf, int size) throws IOException {
		InputStream is = new FileInputStream(inf);
		OutputStream os = new FileOutputStream(outf);
		copy(is, os, size);
	}

	public static void copy(InputStream ins, File outf, int size)
			throws IOException {
		OutputStream os = new FileOutputStream(outf);
		copy(ins, os, size);
	}

	public static void copy(InputStream ins, String outUrl, int size)
			throws IOException {
		OutputStream os = new FileOutputStream(new File(outUrl));
		copy(ins, os, size);
	}

	public static void copy(InputStream ins, String outUrl) throws IOException {
		File outF = new File(outUrl);
		OutputStream os = new FileOutputStream(outF);
		copy(ins, os, FileUtil.DEFAULT_BUFFER_SIZE);
	}

	public static void copy(String inUrl, String outUrl, int size)
			throws IOException {
		File inf = new File(inUrl);
		File outf = new File(outUrl);
		copy(inf, outf, size);
	}

	public static void copy(String inUrl, File outf, int size)
			throws IOException {
		File inf = new File(inUrl);
		copy(inf, outf, size);
	}

	public static void copy(File inf, String outUrl, int size)
			throws IOException {
		File outf = new File(outUrl);
		copy(inf, outf, size);
	}

	public static void copy(String inUrl, String outUrl) throws IOException {
		copy(inUrl, outUrl, DEFAULT_BUFFER_SIZE);
	}

	public static void copy(File inf, File outf) throws IOException {
		copy(inf, outf, DEFAULT_BUFFER_SIZE);
	}

	public static void copy(File inf, String outUrl) throws IOException {
		File outf = new File(outUrl);
		copy(inf, outf);
	}

	public static void copy(String inUrl, File outf) throws IOException {
		File inf = new File(inUrl);
		copy(inf, outf);
	}
	
	/**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    public static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    /**
     *测试
     */
    public static void main(String[] args) {
        doDeleteEmptyDir("new_dir1");
        String newDir2 = "new_dir2";
        boolean success = deleteDir(new File(newDir2));
        if (success) {
            System.out.println("Successfully deleted populated directory: " + newDir2);
        } else {
            System.out.println("Failed to delete populated directory: " + newDir2);
        }     
    }
}
