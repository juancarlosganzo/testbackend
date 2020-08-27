package me.juancarlosganzo.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.Instant;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class ClienteRestService implements IClientRestService {

	private static final String REST_URI = "https://sandbox.ionix.cl/test-tecnico/search?rut=";

	@Override
	public Response requestTest(String param)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException, ClientProtocolException, IOException, ParseException {

		Instant start = Instant.now();

		String query = encrypt(param);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(REST_URI + query);
		getRequest.addHeader("accept", "application/json");
		HttpResponse response = httpClient.execute(getRequest);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException("Failed with HTTP error code : " + statusCode);
		}
		HttpEntity httpEntity = response.getEntity();
		String apiOutput = EntityUtils.toString(httpEntity);

		Gson gson = new Gson();
		ResponseTest responseTest = gson.fromJson(apiOutput, ResponseTest.class);

		Instant finish = Instant.now();
		Long timeElapsed = Duration.between(start, finish).toMillis();

		Response responseResult = new Response();

		responseResult.setResponseCode(responseTest.getResponseCode());
		responseResult.setDescription(responseTest.getDescription());
		responseResult.setElapsedTime(timeElapsed.toString());
		ResponseResult responseResultD = new ResponseResult();
		responseResultD.setRegisterCount(responseTest.getResult().getItems().size());
		responseResult.setResult(responseResultD);

		return responseResult;
	}

	private String encrypt(String text)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

		DESKeySpec keySpec = new DESKeySpec("ionix123456".getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = keyFactory.generateSecret(keySpec);
		byte[] cleartext = text.getBytes("UTF8");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(cipher.ENCRYPT_MODE, desKey);
		String encryptedRut = Base64.encodeBase64String(cipher.doFinal(cleartext));
		return encryptedRut;

	}

}
