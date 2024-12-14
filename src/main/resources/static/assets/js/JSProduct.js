let quantityWantToBuy = document.querySelector('.quantity-buttons--number').innerText;

document.querySelector('.quantity-buttons--minus').onclick = function() {
    quantityWantToBuy > 1 ? quantityWantToBuy -= 1 : quantityWantToBuy = 1;
    document.querySelector('.quantity-buttons--number').innerText = quantityWantToBuy;
}

document.querySelector('.quantity-buttons--plus').onclick = function() {
    quantityWantToBuy = Number(quantityWantToBuy) + 1;
    document.querySelector('.quantity-buttons--number').innerText = quantityWantToBuy;
}

const $ = document.querySelector.bind(document)
const $$ = document.querySelectorAll.bind(document)

const tabs = $$('.product-content__tabs-item')
const panes = $$('.product-content__tabs-content > div')

const tabActive = $('.product-content__tabs-item.tabs-active')

tabs.forEach((tab, index) => {
    const pane = panes[index]
    tab.onclick = function() {
        $('.product-content__tabs-item.tabs-active').classList.remove('tabs-active')
        $('.tabs-content__item.tabs-active').classList.remove('tabs-active')

        this.classList.add('tabs-active')
        pane.classList.add('tabs-active')
    }
});


//Chức năng AddToCart
function addToCart() {
    var productId = document.querySelector('.product-content__text__title').getAttribute('data-product-id');
    var quantity = document.querySelector('.quantity-buttons--number').innerText; // Lấy số lượng từ giao diện người dùng

    // Gửi yêu cầu đến API với các tham số product_id, quantity
    fetch('/api/cartitems', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            product_id: productId,
            quantity: quantity
        })
    })
        .then(response => response.json())
        .then(data => {
            alert('Product added to cart!');
        })
        .catch(error => {
            console.error('Error:', error);
        });
}



