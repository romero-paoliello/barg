package com.barganha.service;

import org.springframework.stereotype.Service;

@Service
public class CryptoServiceImpl implements CryptoService {

	@Override
	public String crypt(String string) {
		// TODO
		return string;
		//return Crypto.sign(string);
	}

}
