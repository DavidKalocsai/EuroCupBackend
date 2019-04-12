-- ---------------------------
-- Voucher table create
-- ---------------------------
CREATE TABLE IF NOT EXISTS euro_cup.voucher (
	id BIGINT NOT NULL AUTO_INCREMENT,
    voucher VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    territory VARCHAR(10) NOT NULL,
    created DATE NOT NULL,
    draw_status VARCHAR(10) DEFAULT 'nodraw',    
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ---------------------------
-- Indexes
-- ---------------------------
CREATE UNIQUE INDEX voucher_pk_index
ON euro_cup.voucher (id);

CREATE UNIQUE INDEX voucher_voucher_u_index
ON euro_cup.voucher (voucher);

CREATE UNIQUE INDEX voucher_email_u_index
ON euro_cup.voucher (email);
