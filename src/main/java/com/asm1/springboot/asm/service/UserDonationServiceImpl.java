package com.asm1.springboot.asm.service;

import com.asm1.springboot.asm.entity.UserDonation;
import com.asm1.springboot.asm.repo.UserDonationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement Service cho  UserDonationService.
 *
 * Class này triển khai các phương thức được định nghĩa trong interface  UserDonationService
 * và  UserDonationRepository
 * để xử lý logic kinh doanh liên quan đến Donation.
 */
@Service
public class UserDonationServiceImpl implements UserDonationService{

    @Autowired
    private UserDonationRepository userDonationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDonation> findAll() {
        return userDonationRepository.findAll();
    }

    @Override
    public int getTotalDonationAmount(String id) {
        String nativeQuery = "SELECT SUM(money) FROM user_donation WHERE donation_id LIKE CONCAT('%', :id, '%') AND status = 1";
        Query query = entityManager.createNativeQuery(nativeQuery);
        query.setParameter("id", id);

        Object result = query.getSingleResult();
        return result != null ? Integer.parseInt(result.toString()) : 0;
    }

    @Override
    public List<UserDonation> findByDonationId(String id) {
        List<UserDonation> donationList = findAll();
        List<UserDonation> filteredList = new ArrayList<>();
        for (UserDonation userDonation : donationList) {
            if (userDonation.getDonation().getId().equals(id)) {
                filteredList.add(userDonation);
            }
        }
        return filteredList;

    }

    @Override
    public UserDonation updateById(UserDonation userDonation) {
        return userDonationRepository.save(userDonation);
    }

    @Override
    public UserDonation save(UserDonation userDonation) {
        return userDonationRepository.save(userDonation);
    }

    @Override
    public UserDonation findById(int id) {
        return userDonationRepository.findById(id);
    }
}
