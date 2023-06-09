package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.UploadProductRequestDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadRequestOutputDto;
import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.UploadProductRequest;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import com.example.bidunitedapi.bidunitedapi.mapper.ProductMapper;
import com.example.bidunitedapi.bidunitedapi.mapper.ProductRequestMapper;
import com.example.bidunitedapi.bidunitedapi.repository.UploadProductRequestRepository;
import com.example.bidunitedapi.bidunitedapi.repository.UserRepository;
import com.example.bidunitedapi.bidunitedapi.service.UploadProductRequestService;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UploadProductRequestServiceImpl implements UploadProductRequestService {
    @Autowired
    private UploadProductRequestRepository uploadProductRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addProductRequest(UploadProductRequestDto uploadProductRequestDto) {
        UploadProductRequest uploadProductRequest=new UploadProductRequest();
        uploadProductRequest= ProductRequestMapper.mapToRequest(uploadProductRequestDto);
        uploadProductRequest.setUser(userRepository.findById(uploadProductRequestDto.getUserId()));
        uploadProductRequestRepository.save(uploadProductRequest);
    }

    @Override
    public List<UploadProductRequestDto> uploadRequestList() {
        List<UploadProductRequest> uploadProductRequest=uploadProductRequestRepository.findAll();
        return null;
    }

    @Override
    public List<UploadRequestOutputDto> getAllUnApprovedRequests() {
        List<UploadProductRequest> uploadProductRequestList=uploadProductRequestRepository.findAll();
        List<UploadRequestOutputDto> list=new ArrayList<>();
        for (UploadProductRequest item:uploadProductRequestList
             ) {
            if(!item.isApproved() && !item.isRejected()){
                UploadRequestOutputDto uploadItem=ProductRequestMapper.mapToOutputDto(item);
                list.add(uploadItem);
            }
        }
        return list;
    }

    @Override
    public UploadProductRequestDto findById(Long id) {
        UploadProductRequest uploadProductRequest=uploadProductRequestRepository.findById(id).get();
        return ProductRequestMapper.mapToDto(uploadProductRequest);
    }

    @Override
    public void deleteById(Long id) {
        uploadProductRequestRepository.deleteById(id);
    }

    @Override
    public void updateRequest(UploadProductRequestDto uploadProductRequestDto) {
        UploadProductRequest uploadProductRequest=ProductRequestMapper.mapToRequest(uploadProductRequestDto);
        uploadProductRequest.setUser(userRepository.findById(uploadProductRequestDto.getUserId()));
        uploadProductRequestRepository.save(uploadProductRequest);
    }

    @Override
    public List<UploadRequestOutputDto> getRequestsByUser(String username) {
        User user=userRepository.findByUsername(username);
        List<UploadProductRequest> list=uploadProductRequestRepository.findByUser(user);
        List<UploadRequestOutputDto> outputList=new ArrayList<>();
        for (UploadProductRequest item:list
        ) {
            if(item.isApproved() && !item.isRejected()){
                outputList.add(ProductRequestMapper.mapToOutputDto(item));
            }
        }
        return outputList;
    }

    @Override
    public List<UploadRequestOutputDto> getRequestsPendingByUser(String username) {
        User user=userRepository.findByUsername(username);
        List<UploadProductRequest> list=uploadProductRequestRepository.findByUser(user);
        List<UploadRequestOutputDto> outputList=new ArrayList<>();
        for (UploadProductRequest item:list
             ) {
            if(!item.isApproved() && !item.isRejected()){
                outputList.add(ProductRequestMapper.mapToOutputDto(item));
            }
        }
        return outputList;
    }

    @Override
    public List<UploadRequestOutputDto> getRequestsDeclinedByUser(String username) {
        User user=userRepository.findByUsername(username);
        List<UploadProductRequest> list=uploadProductRequestRepository.findByUser(user);
        List<UploadRequestOutputDto> outputList=new ArrayList<>();
        for (UploadProductRequest item:list
        ) {
            if(!item.isApproved() && item.isRejected()==true){
                outputList.add(ProductRequestMapper.mapToOutputDto(item));
            }
        }
        return outputList;
    }


}