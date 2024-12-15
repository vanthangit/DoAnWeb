package vn.iotstar.AloTra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.entity.ProductFeedback;
import vn.iotstar.AloTra.repository.ProductFeedbackRepository;
import vn.iotstar.AloTra.service.IProductFeedbackService;

@Service
public class ProductFeedbackService implements IProductFeedbackService {

    @Autowired
    ProductFeedbackRepository productFeedbackRepository;
    @Override
    public void createFeedback(ProductFeedback feedback) {
        productFeedbackRepository.save(feedback);
    }
}
