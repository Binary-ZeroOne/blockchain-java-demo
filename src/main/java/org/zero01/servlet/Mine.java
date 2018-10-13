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

//该Servlet用于运行工作算法的证明来获得下一个证明，也就是所谓的挖矿
@WebServlet("/mine")
public class Mine extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlockChain blockChain = BlockChain.getInstance();
		Map<String, Object> lastBlock = blockChain.lastBlock();
		long lastProof = Long.parseLong(lastBlock.get("proof") + "");
		long proof = blockChain.proofOfWork(lastProof);

		// 给工作量证明的节点提供奖励，发送者为 "0" 表明是新挖出的币
		String uuid = (String) this.getServletContext().getAttribute("uuid");
		blockChain.newTransactions("0", uuid, 1);

		// 构建新的区块
		Map<String, Object> newBlock = blockChain.newBlock(proof, null);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "New Block Forged");
		response.put("index", newBlock.get("index"));
		response.put("transactions", newBlock.get("transactions"));
		response.put("proof", newBlock.get("proof"));
		response.put("previous_hash", newBlock.get("previous_hash"));

		// 返回新区块的数据给客户端
		resp.setContentType("application/json");
		PrintWriter printWriter = resp.getWriter();
		printWriter.println(new JSONObject(response));
		printWriter.close();
	}
}
