package org.example;


import lombok.SneakyThrows;
import org.restframework.security.AES.core.CryptoAlgorithm;
import org.restframework.security.AES.core.StringSecurity;
import org.restframework.security.AES.utils.InitVector;
import org.restframework.security.AES.utils.Key;
import org.restframework.security.hashing.Hashing;
import org.restframework.security.hashing.MD5Hash;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.*;
import org.restframework.web.core.builders.Modifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


@EnableRestConfiguration
@RestApi(
    APIS= {
        @API(
            endpoint="/tools/api/testing1",
            model=@Model(generic = "UUID",
                        tableName = "test_table1",
                        apiName = "Test1",
                        fields = {
                                @FieldData(access = Modifier.PRIVATE, datatype = "int", name = "testField"),
                                @FieldData(access = Modifier.PRIVATE, datatype = "double", name = "testField2"),
                        }
                ),
            apiName="Test1",
            basePackage = "org.example.test.test1"
        ),
        @API(
            endpoint="/tools/api/testing2",
            model=@Model(generic = "UUID",
                        tableName = "test_table2",
                        apiName = "Test2",
                        fields = {
                                @FieldData(access = Modifier.PRIVATE, datatype = "int", name = "testField"),
                                @FieldData(access = Modifier.PRIVATE, datatype = "double", name = "testField2"),
                        }
                    ),
            apiName="Test2",
            basePackage = "org.example.test.test2"
        )
    }
)
@SpringBootApplication
public class ExampleApp {
    @SneakyThrows
    public static void main(String[] args) {
        Hashing<String> hasher = new MD5Hash<>("Hello world");
        System.out.println(Arrays.toString(hasher.getMessage()));

        String input = "Some very important text that needs to be encrypted";
        SecretKey key = Key.generateKey(128);
        CryptoAlgorithm algorithm = CryptoAlgorithm.AES_CBC_PKCS5_PADDING;
        IvParameterSpec iv = new InitVector().getParameter();
        String cipherText = StringSecurity.encryptString(algorithm, input, key, iv);
        String plainText = StringSecurity.decryptString(algorithm, cipherText, key, iv);
        System.out.println(cipherText);
        System.out.println(plainText);

        WebApp app = new WebApp(ExampleApp.class);
        app.run(ExampleApp.class, args);
    }

}