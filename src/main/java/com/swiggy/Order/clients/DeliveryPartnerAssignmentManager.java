package com.swiggy.Order.clients;

import com.swiggy.Order.exceptions.GrpcServiceUnavailableException;
import com.swiggy.Order.exceptions.GrpcUnknownException;
import com.swiggy.Order.exceptions.NoAvailableDeliveryPartnerException;
import fulfillment.FulfillmentGrpc;
import fulfillment.Location;
import fulfillment.NearestDeliveryPartnerResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;

import static com.swiggy.Order.constants.ResponseMessage.*;

@Component
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
            if (s.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new NoAvailableDeliveryPartnerException(DELIVERY_PARTNER_UNAVAILABLE);
            }
            if (s.getStatus().getCode() == Status.Code.UNAVAILABLE) {
                throw new GrpcServiceUnavailableException(SERVICE_UNAVAILABLE);
            }
            throw new GrpcUnknownException(GRPC_UNKNOWN_ERROR);
        }
    }
}
