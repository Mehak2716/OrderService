package com.swiggy.Order.clients;

import com.swiggy.Order.exceptions.GrpcServiceUnavailableException;
import fulfillment.FulfillmentGrpc;
import fulfillment.Location;
import fulfillment.NearestDeliveryPartnerResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import static com.swiggy.Order.constants.ResponseMessage.*;

public class DeliveryPartnerAssignmentManager {

    private  final static int grpcServerPort = 9000;
    public static int getNearestDeliveryPartner(double xCordinate, double yCordinate){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", grpcServerPort).usePlaintext().build();
        FulfillmentGrpc.FulfillmentBlockingStub client = FulfillmentGrpc.newBlockingStub(channel);
        try {
            NearestDeliveryPartnerResponse response = client.getNearestDeliveryPartner(
                    Location.newBuilder().setXCordinate((float) xCordinate)
                            .setYCordinate((float) yCordinate)
                            .build());


            return (int) response.getId();
        } catch (StatusRuntimeException s) {
            throw new GrpcServiceUnavailableException(SERVICE_UNAVAILABLE);
        }
    }
}
