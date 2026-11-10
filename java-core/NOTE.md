# Abstract Class

**Định nghĩa:** lớp không thể tạo đối tượng (không `new` được); dùng để chia sẻ trạng thái và phần cài đặt sẵn cho các lớp con (quan hệ "là một" - is-a).

- **Phương thức:** abstract và không abstract (có cả static, final, constructor)
- **Biến:** final, không final, static và không static (có thể lưu trạng thái)
- **Cài đặt:** có thể implement interface mà không cần cài đặt hết các phương thức
- **Kế thừa:** chỉ extends được một lớp (abstract hoặc lớp thường)
- **Phạm vi truy cập:** thành viên có thể là public, protected, package-private hoặc private (phương thức abstract không được private)

# Interface

**Định nghĩa:** bản hợp đồng quy định một lớp làm được gì (quan hệ "làm được" - can-do); không có trạng thái, không có constructor.

- **Phương thức:** abstract, default, static và private (Java 9+)
- **Biến:** chỉ là hằng số, mặc định public static final
- **Cài đặt:** không implement gì cả; lớp mới là bên dùng `implements` để cài đặt interface
- **Kế thừa:** extends được nhiều interface; một lớp có thể implement nhiều interface
- **Phạm vi truy cập:** thành viên mặc định là public; phương thức có thể private (Java 9+), không bao giờ protected

# Generics

**Định nghĩa:** tham số hóa kiểu dữ liệu (`<T>`) cho lớp, interface, phương thức; viết code một lần dùng cho nhiều kiểu, an toàn kiểu lúc biên dịch, không cần ép kiểu.

- **Khai báo:** lớp/interface `class Box<T>`, phương thức `<T> T get(T x)`
- **Kiểu dùng được:** chỉ kiểu tham chiếu (Integer, String...), không dùng kiểu nguyên thủy (int, double...)
- **Giới hạn kiểu:** `<T extends Number>` (T phải là Number hoặc lớp con)
- **Wildcard:** `<?>`, `<? extends T>` (đọc), `<? super T>` (ghi)
- **Type erasure:** thông tin kiểu bị xóa khi chạy, nên không `new T()`, không `instanceof List<String>`

# Lambda Expression

**Định nghĩa:** hàm ẩn danh viết ngắn gọn (Java 8+), dùng để cài đặt phương thức duy nhất của một functional interface; cho phép truyền hành vi như một tham số.

- **Cú pháp:** `(tham số) -> biểu thức` hoặc `(tham số) -> { câu lệnh; }`, ví dụ `(a, b) -> a + b`
- **Functional interface:** interface có đúng một phương thức abstract (`Runnable`, `Comparator`, `Function`, `Predicate`...), có thể đánh dấu `@FunctionalInterface`
- **Kiểu tham số:** có thể bỏ, trình biên dịch tự suy ra từ ngữ cảnh
- **Biến bên ngoài:** chỉ dùng được biến final hoặc effectively final (không bị gán lại)
- **Method reference:** dạng rút gọn khi lambda chỉ gọi một phương thức có sẵn, ví dụ `String::length`, `System.out::println`

# Threads

**Định nghĩa:** luồng thực thi độc lập bên trong một tiến trình; nhiều thread chạy song song, dùng chung bộ nhớ heap của tiến trình nhưng mỗi thread có stack riêng.

- **Cách tạo:** extends `Thread` và override `run()`, hoặc truyền `Runnable` (thường là lambda) vào `new Thread(...)`; gọi `start()` để chạy (gọi `run()` chỉ chạy trên thread hiện tại)
- **Vòng đời:** NEW → RUNNABLE → BLOCKED / WAITING / TIMED_WAITING → TERMINATED
- **Đồng bộ hóa:** dữ liệu dùng chung dễ bị race condition; dùng `synchronized`, `Lock`, `volatile` hoặc lớp atomic (`AtomicInteger`...)
- **Thread pool:** dùng `ExecutorService` (`Executors.newFixedThreadPool(n)`) để tái sử dụng thread thay vì tạo mới liên tục; `Callable` + `Future` để lấy kết quả trả về
- **Virtual thread (Java 21+):** thread nhẹ do JVM quản lý, tạo được hàng triệu thread, ví dụ `Thread.ofVirtual().start(...)`, `Executors.newVirtualThreadPerTaskExecutor()`
