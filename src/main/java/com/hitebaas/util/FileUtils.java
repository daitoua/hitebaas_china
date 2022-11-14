package com.hitebaas.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.hitebaas.data.trade.TradeBodyPool;
import com.hitebaas.util.comom.CommomUtil;

public class FileUtils{
    /**
    * 压缩文件
    *
    * @param sourceFilePath 源文件路径
    * @param zipFilePath    压缩后文件存储路径
    * @param zipFilename    压缩文件名
    */
   public static void compressToZip(String sourceFilePath, String zipFilePath, String zipFilename,int zipIndex) {
       File sourceFile = new File(sourceFilePath);
       File zipPath = new File(zipFilePath);
       if (!zipPath.exists()) {
           zipPath.mkdirs();
       }
       File zipFile = new File(zipPath + File.separator + zipFilename);
       try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
    	   StringBuffer sb = new StringBuffer();
    	   sb=writeZip(sourceFile, "", zos,sb);
    	   writeZip(sb,zipIndex);
    	   
           //文件压缩完成后，删除被压缩文件
           //boolean flag = deleteDir(sourceFile);
         
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e.getMessage(), e.getCause());
       }
   }
   
   /**
    * 遍历所有文件，压缩
    *
    * @param file       源文件目录
    * @param parentPath 压缩文件目录
    * @param zos        文件流
    */
   public static StringBuffer writeZip(File file, String parentPath, ZipOutputStream zos,StringBuffer sb) {
       if (file.isDirectory()) {
           //目录
           parentPath += file.getName() + File.separator;
           File[] files = file.listFiles();
           for (File f : files) {
               writeZip(f, parentPath, zos,sb);
           }
       } else {
           //文件
           try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
               //指定zip文件夹
        	   /*if(file.getName().split("\\.")[1].equals("txt")) {
        		   FileInputStream is=new FileInputStream(file);
        		   InputStreamReader streamReader = new InputStreamReader(is);
        		   BufferedReader reader = new BufferedReader(streamReader);
        		   StringBuilder stringBuilder = new StringBuilder();
        		   String line;
        		   while ((line = reader.readLine()) != null) {
        			     // stringBuilder.append(line);
        			     stringBuilder.append(line);
        		 }
        		   reader.close();
        		    is.close();
        		    sb.append(stringBuilder);
        		    sb.append("\n");
        	   }*/
               ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
               zos.putNextEntry(zipEntry);
               int len;
               byte[] buffer = new byte[1024 * 10];
               while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                   zos.write(buffer, 0, len);
                   zos.flush();
               }
           } catch (Exception e) {
               e.printStackTrace();
               throw new RuntimeException(e.getMessage(), e.getCause());
           }
       }
       return sb;
   }
   public static void  writeZip(StringBuffer sb,int zipIndex) {
	   
	   
	   File file = new File (DataUtils.getRelativePath("/ws/block"+zipIndex+".txt"));
	   try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(sb.toString());
        bw.flush();
        bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   /**
    * 删除文件夹
    *
    * @param dir
    * @return
    */
   public static boolean deleteDir(File dir) {
       if (dir.isDirectory()) {
           String[] children = dir.list();
           for (int i = 0; i < children.length; i++) {
               boolean success = deleteDir(new File(dir, children[i]));
               if (!success) {
                   return false;
               }
           }
       }
       //删除空文件夹
       return dir.delete();
   }
	    /**
	     * zip文件解压
	     * @param inputFile  待解压文件夹/文件
	     * @param destDirPath  解压路径
	     */
	    public static List<TradeBodyPool> zipUncompress(String inputFile,String destDirPath) throws Exception {
	        File srcFile = new File(inputFile);//获取当前压缩文件
	        List<TradeBodyPool> tbps=new ArrayList<TradeBodyPool>();
	        // 判断源文件是否存在
	        if (!srcFile.exists()) {
	            throw new Exception(srcFile.getPath() + "所指文件不存在");
	        }
	        ZipFile zipFile = new ZipFile(srcFile);//创建压缩文件对象
	        //开始解压
	        Enumeration<?> entries = zipFile.entries();
	        while (entries.hasMoreElements()) {
	            ZipEntry entry = (ZipEntry) entries.nextElement();
	            // 如果是文件夹，就创建个文件夹
	            if (entry.isDirectory()) {
	                String dirPath = destDirPath + "/" + entry.getName();
	                srcFile.mkdirs();
	            } else {
	                // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
	                File targetFile = new File(destDirPath + "/" + entry.getName());
	                // 保证这个文件的父文件夹必须要存在
	                if (!targetFile.getParentFile().exists()) {
	                    targetFile.getParentFile().mkdirs();
	                }
	                targetFile.createNewFile();
	                // 将压缩文件内容写入到这个文件中
	                InputStream is = zipFile.getInputStream(entry);
	                FileOutputStream fos = new FileOutputStream(targetFile);
	                int len;
	                byte[] buf = new byte[1024];
	                while ((len = is.read(buf)) != -1) {
	                    fos.write(buf, 0, len);
	                }
	                String str=CommomUtil.readerFile(targetFile);
	                if(str!=null) {
					 TradeBodyPool tbp=new Gson().fromJson(str, TradeBodyPool.class);
					 tbps.add(tbp);
	                }
	                //System.out.println(str);

	                // 关流顺序，先打开的后关闭
	                fos.close();
	                is.close();
	            }
	        }
	        return tbps;
	    }
	    
	    public static File createFile(String path) {
	    	
	    	File file = new File(path);
	    	if(!file.exists()){
	    		try {
	    			file.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
	    	}
	    	return file;
	    }
	    
	    
	    public void inputstreamtofile(InputStream ins,File file) throws Exception{
	    	   OutputStream os = new FileOutputStream(file);
	    	   int bytesRead = 0;
	    	   byte[] buffer = new byte[8192];
	    	   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
	    	      os.write(buffer, 0, bytesRead);
	    	   }
	    	   os.close();
	    	   ins.close();
	    }
	    
	    /**
	     * 将文件转换成byte数组
	     * @param filePath
	     * @return
	     */
	    public static byte[] File2byte(File tradeFile){
	        byte[] buffer = null;
	        try
	        {
	            FileInputStream fis = new FileInputStream(tradeFile);
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            byte[] b = new byte[1024];
	            int n;
	            while ((n = fis.read(b)) != -1)
	            {
	                bos.write(b, 0, n);
	            }
	            fis.close();
	            bos.close();
	            buffer = bos.toByteArray();
	        }catch (FileNotFoundException e){
	            e.printStackTrace();
	        }catch (IOException e){
	            e.printStackTrace();
	        }
	        return buffer;
	    }
}
   
