package org.roof.safety.service;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.roof.safety.entity.FileRecover;

/**
 * 防篡改任务
 */
public class PreventTamperTask {

	public void run() {
		mainTask();
	}

	/**
	 * 防篡改任务
	 */
	private void mainTask() {
		PreventTamperUtil preventTamper = new PreventTamperUtil();
		List<FileRecover> listDB = preventTamper.loadFileRecoverList();
		List<String> listDir = preventTamper.readFilesFromFolder(
				PreventTamperUtil.protectDir, null);

		for (int i = 0; i < listDB.size(); i++) {
			FileRecover temp = listDB.get(i);
			FileRecover fileRecover = preventTamper.compareTo(temp);
			if (fileRecover != null) {// 删除，修改的被恢复
				preventTamper.updateFileRecover(fileRecover);
				System.out.println("在【" + new Date() + "】，篡改的文件【"
						+ temp.getUrl() + "】被恢复......");
			}
			for (int j = 0; j < listDir.size(); j++) {// 比较当前目录文件数量和数据库文件数量的不同
				String s = listDir.get(j);
				if (s.equals(temp.getUrl())) {
					listDir.remove(j);
				}
			}
		}
		Collections.reverse(listDir);
		for (int j = 0; j < listDir.size(); j++) {// 删除新增的文件
			String s = listDir.get(j);
			File f = new File(s);
			File p = new File(f.getParent());
			f.delete();
			p.delete();
			System.out.println("在【" + new Date() + "】，新增的文件【" + s
					+ "】被删除......");
		}
		System.out.println("监控中......");
	}

}
