package com.truxapiv2.util;

import java.net.URL;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

public class S3 {

	public static URL getSignedURL(String bucket, String path){
		
		URL signedURL ;
		AWSCredentials credentials = new BasicAWSCredentials("AKIAIJALK2XEKW3HG4EA", "EEPU2WCXhD8NWI4lBSHpZ81mQVx9RxWPiHcAWA6p");
		ClientConfiguration clientConfig = new ClientConfiguration();
		
		clientConfig.setProtocol(Protocol.HTTP);
		
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint("s3-ap-southeast-1.amazonaws.com");
		
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, path);
		//GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest("truxs3", "client/ff04274d-64c4-40ff-a37f-ddc803a7db34.png");
		signedURL = conn.generatePresignedUrl(request);
		
		return signedURL;
	}

}
/* import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
public class S3 {
public static void main(String[] args) {
  AWSCredentials credentials = new BasicAWSCredentials("Access Key", "Secret Key");
  ClientConfiguration clientConfig = new ClientConfiguration();
  clientConfig.setProtocol(Protocol.HTTP);
   AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
   conn.setEndpoint("s3-ap-southeast-1.amazonaws.com");
   GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest("Bucket Name", "foldername/filename");
   System.out.println(conn.generatePresignedUrl(request));
  }
}  */