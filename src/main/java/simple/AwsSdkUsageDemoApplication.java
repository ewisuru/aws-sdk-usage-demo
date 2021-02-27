package simple;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUserPoolsRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUserPoolsResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersResponse;

public class AwsSdkUsageDemoApplication {
    public static void main(String[] args) {
        System.out.println("Hello");
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder()
                .profileName("cognito-admin")
                .build();

        CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(credentialsProvider)
                .build();

        //listUserPools(cognitoClient);

        ListUsersResponse listUsersResponse = cognitoClient.listUsers(ListUsersRequest.builder()
                .userPoolId("ap-southeast-1_BVnzIKmk5")
                .build());
        listUsersResponse.users().forEach(user -> {
            System.out.println(user);
        });
    }

    private static void listUserPools(CognitoIdentityProviderClient cognitoClient) {

        ListUserPoolsResponse listUserPools = cognitoClient.listUserPools(ListUserPoolsRequest.builder()
                .maxResults(10)
                .build());
        listUserPools.userPools().forEach(System.out::println);
    }

}
