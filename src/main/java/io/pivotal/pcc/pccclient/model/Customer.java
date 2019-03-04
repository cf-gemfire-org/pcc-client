package io.pivotal.pcc.pccclient.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Region("Customer")
@Data
@RequiredArgsConstructor(staticName = "of" )
@ToString(exclude = {"profilePic"})
public class Customer {

    @Id
    @NonNull
    Integer id;

    @NonNull
    String name;

    @NonNull
    int age;

    @NonNull
    byte[] profilePic;
}
