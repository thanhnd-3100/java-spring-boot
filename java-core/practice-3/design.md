# Practice 3 - Thiết kế lớp (Requirement 1)

## Sơ đồ lớp

```
                 Vehicle (abstract) ───có──> Owner
        ┌───────────────┼───────────────┐
       Car          Motorbike         Truck

Vehicle ───có──> Manufacturer (enum: HONDA, YAMAHA, TOYOTA, SUZUKI)
VehicleManager ──chứa (List<Vehicle>)──> Vehicle
```

## Vehicle (abstract class)

- **Thuộc tính:** `number` (biển số, không đổi), `manufacturer`, `year`, `color`, `owner`
- **Ràng buộc:** biển số đúng 5 ký tự chữ/số (tự chuyển chữ hoa); năm sản xuất > 2000 và <= năm hiện tại
- **Phương thức abstract:** `getType()`, `getDetails()` (lớp con tự in thông tin riêng)

## Các lớp con (concrete class)

| Lớp | Thuộc tính riêng | Ràng buộc |
|---|---|---|
| `Car` | số chỗ ngồi, loại động cơ | số chỗ > 0 |
| `Motorbike` | dung tích (cc) | > 0 |
| `Truck` | trọng tải (tấn) | > 0 |

## Owner

- **Thuộc tính:** `idNumber` (CMND/CCCD, không đổi), `fullName`, `email`
- **Ràng buộc:** CMND đúng 12 chữ số; email đúng định dạng
- Một chủ xe có thể sở hữu nhiều xe; nhiều xe dùng chung **một** đối tượng `Owner`

## Manufacturer (enum)

Chỉ có 4 hãng: Honda, Yamaha, Toyota, Suzuki. Dùng enum nên không thể tạo hãng ngoài danh sách.

## VehicleManager

- **Thuộc tính:** `List<Vehicle> vehicles`
- **Phương thức:**
  - `add()`: báo lỗi nếu trùng biển số, hoặc CMND đã thuộc về một chủ xe khác
  - `findByNumber()`, `findByOwnerId()`, `findOwner()`
  - `removeByManufacturer()`: xóa toàn bộ xe của một hãng, trả về số xe đã xóa
  - `countByManufacturer()`, `getTopManufacturers()` (trả về tất cả các hãng cùng số xe cao nhất)
  - `sortByManufacturerCount()`: xếp xe theo hãng, hãng nhiều xe đứng trước
  - `countByType()`: thống kê số xe theo loại

## Quan hệ

- **Kế thừa:** `Car`, `Motorbike`, `Truck` kế thừa `Vehicle`.
- **Đa hình:** `VehicleManager` chỉ làm việc với kiểu `Vehicle`; `getType()`, `getDetails()`, `toString()` chạy đúng phiên bản của từng lớp con.
- **Đóng gói:** thuộc tính `private`, chỉ thay đổi qua setter có kiểm tra ràng buộc.
- **Kết hợp (association):** `Vehicle` có một `Owner`; `VehicleManager` chứa danh sách `Vehicle`.
