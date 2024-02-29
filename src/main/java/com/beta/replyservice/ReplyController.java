package com.beta.replyservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class ReplyController {

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}

	@GetMapping("/v2/reply/{message}")
	public ReplyMessage replyingV2(@PathVariable String message) {
		try {
			String[] parts = message.split("-");
			if (parts.length != 2 || !isValidRule(parts[0]) || !isValidString(parts[1])) {
				return new ReplyMessage("Invalid input");
			}

			String result = applyRules(parts[0], parts[1]);
			return new ReplyMessage(result);
		} catch (Exception e) {
			return new ReplyMessage("Invalid input");
		}
	}

	private boolean isValidRule(String rule) {
		return rule.matches("[12]+");
	}

	private boolean isValidString(String input) {
		return input.matches("[a-z0-9]+");
	}

	private String applyRules(String rule, String input) {
		for (char r : rule.toCharArray()) {
			switch (r) {
				case '1':
					input = new StringBuilder(input).reverse().toString();
					break;
				case '2':
					input = md5Hash(input);
					break;
				default:
					// Ignore invalid rules
			}
		}
		return input;
	}

	private String md5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] digest = md.digest();
			StringBuilder result = new StringBuilder();
			for (byte b : digest) {
				result.append(String.format("%02x", b));
			}
			return result.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not available", e);
		}
	}

}