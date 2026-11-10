# Practice 2 - Thiết kế lớp (Requirement 1)

## Sơ đồ lớp

```
                 Product (abstract)
        ┌───────────────┼───────────────┐
      Food         Electronics       Crockery

ProductManager ──chứa (Product[])──> Product
```

## Product (abstract class)

Lớp cha chứa thông tin chung của mọi loại hàng.

- **Thuộc tính:** `code` (mã hàng, không đổi), `name`, `quantity` (>= 0), `unitPrice` (>= 0)
- **Phương thức chung:** getter/setter có kiểm tra dữ liệu, `getVatAmount()` = số lượng × đơn giá × thuế suất, `toString()`
- **Phương thức abstract (lớp con tự cài đặt):** `getType()`, `getVatRate()`, `evaluateConsumption()`, `getDetails()`

## Các lớp con (concrete class)

| Lớp | Thuộc tính riêng | VAT | Đánh giá tiêu thụ |
|---|---|---|---|
| `Food` | ngày sản xuất, hạn dùng (>= ngày sản xuất), nhà cung cấp | 5% | Còn hàng và đã hết hạn → "Hard to sell" |
| `Electronics` | bảo hành (tháng, >= 0), công suất KW (>= 0) | 10% | Tồn kho < 3 → "Sold" |
| `Crockery` | nhà sản xuất, ngày nhập kho | 10% | Tồn kho > 50 và lưu kho > 10 ngày → "Slow sale" |

Các trường hợp còn lại: `evaluateConsumption()` trả về `null` (không đánh giá).

## ProductManager (lớp quản lý DSHH)

- **Thuộc tính:** `Product[] products` (mảng, tự tăng kích thước khi đầy), `size`
- **Phương thức:** `add()` (thất bại nếu trùng mã), `findByCode()`, `getTotalQuantity(type)`, `getTotalVat(type)`, `displayAll()`, `displaySummary()`

## Quan hệ

- **Kế thừa:** `Food`, `Electronics`, `Crockery` kế thừa `Product`.
- **Đa hình:** `ProductManager` chỉ làm việc với kiểu `Product`; khi gọi `getVatRate()`, `evaluateConsumption()`, `toString()` thì mỗi đối tượng tự chạy phiên bản của lớp con tương ứng.
- **Đóng gói:** thuộc tính `private`, chỉ thay đổi qua setter có kiểm tra ràng buộc.
- **Kết hợp (composition):** `ProductManager` chứa mảng các `Product`.
