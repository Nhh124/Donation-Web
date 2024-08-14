package com.asm1.springboot.asm.service;

import com.asm1.springboot.asm.entity.Donation;
import com.asm1.springboot.asm.repo.DonationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implement Service cho DonationService.
 *
 * Class này triển khai các phương thức được định nghĩa trong interface DonationService
 * và DonationRepository
 * để xử lý logic kinh doanh liên quan đến Donation.
 */
@Service
public class DonationServiceImpl implements DonationService{

    @Autowired
    DonationRepository donationRepository;

    @Override
    public List<Donation> findAll() {

      return donationRepository.findAll();
    }


    @Override
    public Donation findById(String id) {
        return donationRepository.findById(id);
    }

    @Override
    public Donation add(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public Donation update(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public void deleteById(String id) {
        donationRepository.deleteById(id);
    }

    @Override
    public boolean isIdExists(String id) {
        List<Donation> donationList = findAll();

        for (Donation donation : donationList){
            if(donation.getId().equals(id)){
                return true;
            }
        }

        return false;
    }
}
