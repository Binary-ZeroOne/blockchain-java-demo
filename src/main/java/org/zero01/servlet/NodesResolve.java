package org.zero01.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.zero01.core.BlockChain;

// 用于解决冲突
@WebServlet("/nodes/resolve")
public class NodesResolve extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BlockChain blockChain = BlockChain.getInstance();
		boolean replaced = blockChain.resolveConflicts();

		Map<String, Object> response = new HashMap<String, Object>();
		if (replaced) {
			response.put("message", "Our chain was replaced");
			response.put("new_chain", blockChain.getChain());
		} else {
			response.put("message", "Our chain is authoritative");
			response.put("chain", blockChain.getChain());
		}

		resp.setContentType("application/json");
		PrintWriter printWriter = resp.getWriter();
		printWriter.println(new JSONObject(response));
		printWriter.close();
	}
}
