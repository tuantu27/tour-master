package com.example.tour.entity;


import javax.persistence.*;

import com.example.tour.model.dto.AccountsDTO;
import com.example.tour.model.dto.ToursDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="tour")
public class ToursEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tourId;

    private String tourName;
    private String startDate;
    private String duration; // khoang thoi gian
    private String description;
    private String imgName;
    private Long priceAdult;
    private Long priceChildren;
    private String startDes;
    private Long priceInfant;
    private int status;
    private Long quantity;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private AccountsEntity account;

    @OneToMany(mappedBy = "tour")
    private List<ReviewsEntity> lstReview;

 
    @OneToMany(mappedBy = "tour")
    private List<BookingEntity> lstBooking;

    @OneToMany(mappedBy = "tour")
    private List<DateTourEntity> lstDateTour;

    @OneToMany(mappedBy = "tour")
    private List<MultipleTypeTourEntity> lstMulTypeTour;

    public static ToursDTO toDto(ToursEntity toursEntity){
        ToursDTO toursDTO = new ToursDTO();
        toursDTO.setTourId(toursEntity.getTourId());
        toursDTO.setTourName(toursEntity.getTourName());
        toursDTO.setStartDate(toursEntity.getStartDate());
        toursDTO.setDuration(toursEntity.getDuration());
        toursDTO.setDescription(toursEntity.getDescription());
        toursDTO.setImgName(toursEntity.getImgName());
        toursDTO.setPriceAdult(toursEntity.getPriceAdult());
        toursDTO.setStartDes(toursEntity.getStartDes());
        toursDTO.setPriceChildren(toursEntity.getPriceChildren());
        toursDTO.setPriceInfant(toursEntity.getPriceInfant());
        toursDTO.setStatus(toursEntity.getStatus());
        toursDTO.setQuantity(toursEntity.getQuantity());
        toursDTO.setAccountsDTO(toursEntity.getAccount().toDto(toursEntity.getAccount()));

        return toursDTO;


    };



}
