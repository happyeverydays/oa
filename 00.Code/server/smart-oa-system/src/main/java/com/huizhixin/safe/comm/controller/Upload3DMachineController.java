package com.huizhixin.safe.comm.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huizhixin.integrated.comm.LocalFileServerUtil;

@Controller
@RequestMapping("/upload3DMachine")
public class Upload3DMachineController {

	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<String, Object>();
		String fileName = file.getOriginalFilename();

		String respPath = "";
		String path = LocalFileServerUtil.getBasedir();
		File temp = new File(path);
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String newFileName = (new Date()).getTime()
				+ java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5) + suffix;
		respPath = newFileName;
		File savefile = new File(temp, newFileName);

		savefile.mkdirs();
		if (savefile.exists()) {
			savefile.delete();
		} else {
			try {
				savefile.createNewFile();
			} catch (Exception e) {
			}
		}

		try {
			file.transferTo(savefile);

		} catch (Exception e) {
			e.printStackTrace();
			res.put("success", false);
			res.put("msg", "解析文件失败.");
			return res;
		}
		String url = LocalFileServerUtil.getWebUrl(respPath);
		res.put("success", true);
		res.put("url", url);
		res.put("shorturl", respPath);
		return res;
	}

}
