package simple;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

public class AwsSdkUsageDemoApplication {
    public static void main(String[] args) {

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder()
                .profileName("cognito-admin")
                .build();

        CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        String userPoolId = "ap-southeast-1_ksp0kj5aF";

        //createAdmin(cognitoClient,userPoolId , "testuser2", "tsetuser2@gmail.com");
        updatePassword(cognitoClient, userPoolId, "testuser2", "1q2w3e4r5t");


    }

    public static void updatePassword(CognitoIdentityProviderClient cognitoclient,
                                   String userPoolId,
                                   String name,
                                   String newPassword){

        try{
            AdminSetUserPasswordResponse response = cognitoclient.adminSetUserPassword(
                    AdminSetUserPasswordRequest
                            .builder()
                            .username(name)
                            .password("1q2w3e4r5t")
                            .userPoolId(userPoolId)
                            .permanent(true)
                            .build()
            );

            System.out.println("Changed password for user: " + name + "to: " + newPassword);

        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void createAdmin(CognitoIdentityProviderClient cognitoclient,
                                   String userPoolId,
                                   String name,
                                   String email){

        try{
            AdminCreateUserResponse response = cognitoclient.adminCreateUser(
                    AdminCreateUserRequest.builder()
                            .userPoolId(userPoolId)
                            .username(name)
                            .userAttributes(AttributeType.builder()
                                    .name("email")
                                    .value(email)
                                    .name("custom:id")
                                    .value("G2342432423N")
                                    .build())
                            .messageAction("SUPPRESS")
                            .build()
            );

            System.out.println("User " + response.user().username() + "is created. Status: " + response.user().userStatus());

        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        //snippet-end:[cognito.java2.add_login_provider.main]
    }

    private static void listUserPools(CognitoIdentityProviderClient cognitoClient) {

        ListUserPoolsResponse listUserPools = cognitoClient.listUserPools(ListUserPoolsRequest.builder()
                .maxResults(10)
                .build());
        listUserPools.userPools().forEach(System.out::println);
    }

}
