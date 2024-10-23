package com.kimngan.ComesticAdmin.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.repository.SanPhamRepository;

@Service
public class SanPhamServiceImpl implements SanPhamService {

	@Autowired
	private SanPhamRepository sanPhamRepository;

	@Override
	public List<SanPham> getAll() {
		// TODO Auto-generated method stub
		return sanPhamRepository.findAll();
	}

	@Override
	public SanPham create(SanPham sanPham) {
		// Kiểm tra xem sản phẩm có cùng tên đã tồn tại và đang hoạt động không
		if (sanPhamRepository.existsByTenSanPhamAndTrangThai(sanPham.getTenSanPham(), true)) {
			return null; // Sản phẩm đã tồn tại và đang hoạt động
		}
		sanPham.setTrangThai(true); // Đảm bảo trạng thái là hoạt động khi tạo mới
		return sanPhamRepository.save(sanPham);
	}

	@Override
	public SanPham findById(Integer maSanPham) {
		Optional<SanPham> optionalDanhMuc = sanPhamRepository.findById(maSanPham);
		return optionalDanhMuc.orElse(null); // Trả về null nếu không tìm thấy danh mục
	}

	@Override
	public Boolean delete(Integer maSanPham) {
		Optional<SanPham> sanPhamOpt = sanPhamRepository.findById(maSanPham);
		if (sanPhamOpt.isPresent()) {
			SanPham sanPham = sanPhamOpt.get();
			sanPham.setTrangThai(false); // Đánh dấu trạng thái là ngừng hoạt động
			sanPhamRepository.save(sanPham);
			return true;
		}
		return false;
	}

	@Override
	public Boolean update(SanPham sanPham) {
		// Kiểm tra xem sản phẩm có tồn tại trước khi cập nhật
		if (sanPhamRepository.existsById(sanPham.getMaSanPham())) {
			sanPhamRepository.save(sanPham);
			return true;
		}
		return false;
	}

	@Override
	public Page<SanPham> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findAll(pageable);
	}

	@Override
	public Page<SanPham> searchActiveByName(String tenSanPham, Pageable pageable) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findByTenSanPhamContainingIgnoreCaseAndTrangThai(tenSanPham,true, pageable);
	}

	@Override
	public Page<SanPham> findAllActive(Pageable pageable) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findByTrangThaiTrue(pageable);
	}

	@Override
	public Optional<SanPham> findByIdOptional(Integer maSanPham) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findById(maSanPham);
	}

	@Override
	public Boolean existsByTenSanPham(String tenSanPham) {
		return sanPhamRepository.existsByTenSanPhamAndTrangThai(tenSanPham, true);
	}

	@Override
	public List<SanPham> findByTrangThai(Boolean trangThai) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findByTrangThai(trangThai);
	}

	@Override
	public List<SanPham> findByMaSanPhamInAndTrangThai(List<Integer> maSanPham, Boolean trangThai) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findByMaSanPhamInAndTrangThai(maSanPham, trangThai);
	}

	

}
