package vn.iotstar.AloTra.service;

import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.entity.ProductFeedback;

@Service
public interface IProductFeedbackService {
    public void createFeedback(ProductFeedback feedback);
}