
let mainCheckbox = document.querySelector(".select-all-products");

mainCheckbox.onclick = function () {
    let subCheckbox = document.querySelectorAll(".select-product");
    if (mainCheckbox.checked)
        for (let checkbox of subCheckbox)
            checkbox.setAttribute("checked", "checked")
    else if (!mainCheckbox.checked)
        for (let checkbox of subCheckbox)
            checkbox.removeAttribute("checked");
}

let buttonNumbers = document.querySelectorAll(".quantity-buttons--number");
let buttonMinus = document.querySelectorAll(".quantity-buttons--minus");
let buttonPlus = document.querySelectorAll(".quantity-buttons--plus");

for(let i = 0; i < buttonNumbers.length; i++) {
    buttonMinus[i].onclick = function () {
        if(buttonNumbers[i].innerText > 0)
            buttonNumbers[i].innerText -= 1;
        else {
            confirm("Do you want to remove this item from cart?")
            cartItems[i].style.display = "none";
        }

        buttonNumbers[i].innerText > 0 ? buttonNumbers[i].innerText -= 1 : buttonNumbers[i] = 0;
    }
    buttonPlus[i].onclick = function () {
        buttonNumbers[i].innerText = Number(buttonNumbers[i].innerHTML) + 1;
    }
}

let deleteButtons = document.querySelectorAll(".filled-cart__item-delete-button .buttons");
let cartItems = document.querySelectorAll(".filled-cart__item");

for(let i = 0; i < deleteButtons.length; i++)
    deleteButtons[i].onclick = function () {
        cartItems[i].style.display = "none";
    }



// Load Cart function
function loadCart() {
    const cartItemsWrapper = document.querySelector('.filled-cart__items-wrapper');
    cartItemsWrapper.innerHTML = ''; // Xóa nội dung cũ trước khi thêm mới

    // Gọi API để lấy danh sách CartItem
    fetch('/api/cartitems/getCart')
        .then(response => response.json())
        .then(cartItems => {
            cartItems.forEach(item => {
                // Gọi API để lấy thông tin sản phẩm bằng productID
                fetch(`/products/${item.product_id}`)  // Thay thế bằng endpoint API của bạn
                    .then(response => response.json())
                    .then(product => {
                        const cartItem = document.createElement('div');
                        cartItem.className = 'filled-cart__item';

                        cartItem.innerHTML = `
                            <input type="checkbox" class="select-product">
                            <a href="#" class="filled-cart__item-info">
                                <div class="filled-cart__item-img"></div>
                                <p class="filled-cart__item-description">${product.productName}</p>
                            </a>
                            <span class="filled-cart__item-price">$${product.productPrice}</span>
                            <div class="filled-cart__item-quantity">
                                <div class="quantity-buttons">
                                    <div class="quantity-buttons--number" style="margin-left: 114px">${item.quantity}</div>
                                </div>
                            </div>
                            <span class="filled-cart__item-totalPrice">${product.productPrice * item.quantity}</span>
                            <div class="filled-cart__item-delete-button">
                                <button class="buttons">Delete</button>
                            </div>
                        `;

                        cartItemsWrapper.appendChild(cartItem);
                    })
                    .catch(error => console.error('Lỗi khi lấy thông tin sản phẩm:', error));
            });
        })
        .catch(error => console.error('Lỗi khi lấy thông tin giỏ hàng:', error));
}




