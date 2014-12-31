package net.snails.scheduler.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import net.snails.scheduler.service.TechArticleService;

public class NewsUrls {
	
	public static void main(String[] args) throws IOException {
		FileOutputStream out =new FileOutputStream("e:/tech-article.txt");
		int perPageCount =20000;
		int totalPageNum=1;
//		TechNewsService ns = new TechNewsService();
		TechArticleService ns = new TechArticleService();
		long totalCounts =33;//ns.getTechArticleTotal();
		System.out.println("共有："+totalCounts);
		totalPageNum = (int) (Math.ceil((double) totalCounts / (double)perPageCount));
		
		System.out.println("共有："+totalPageNum+"页");
		long offset =1;
		long rows=10000;
		for(int i=0;i<=totalPageNum;i++){
			offset=i*perPageCount;
			
			List urls =null;// ns.getArticleUrls(offset, perPageCount);
			
			for(int j=0;j<urls.size()-1;j++){
				String url =(String)urls.get(j);
				out.write((url+"\n").getBytes());
			}
			System.out.println("插入："+offset);
		}
		
		out.close();
	}
}
