document.getElementById("filter-status").addEventListener("change", function () {
    const status = this.value;
    const page = 0; // Reset to the first page
    fetch(`/owner/voucher?status=${status}&page=${page}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById("voucher-list");
            tbody.innerHTML = "";
            data.vouchers.forEach(voucher => {
                const row = `
                    <tr>
                        <td>${voucher.voucher_id}</td>
                        <td>${voucher.start_date}</td>
                        <td>${voucher.end_date}</td>
                        <td>${voucher.voucher_value}</td>
                        <td>
                            <button onclick="editVoucher(${voucher.voucher_id})" class="btn-action">Chỉnh sửa</button>
                            <button onclick="deleteVoucher(${voucher.voucher_id})" class="btn-action btn-danger">Xóa</button>
                        </td>
                    </tr>`;
                tbody.innerHTML += row;
            });
        });
});