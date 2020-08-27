package me.juancarlosganzo.me;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.juancarlosganzo.client.IClientRestService;

@SpringBootTest
public class ClienteRestServiceTest {
	
	@Autowired
	IClientRestService clientService;
	
	@Test
	void contextLoads() throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, ClientProtocolException, IOException {
		
		assertEquals(clientService.requestTest("1-9").getResult().getRegisterCount(), 3) ;
		
	}
	

}
