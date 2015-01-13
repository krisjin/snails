package net.snails.scheduler.pipeline;

import java.io.FileWriter;
import java.io.IOException;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class GithubUserPipeline implements Pipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		
		FileWriter writer = null;
		StringBuilder sb =new StringBuilder();
		sb.append("{\"author\":").append("\"").append(resultItems.get("author")).append("\" ,");
		sb.append("\"company\":").append("\"").append(resultItems.get("company")).append("\"}").append("\n");
		try {
			writer = new FileWriter("e:/github-user.txt", true);
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
