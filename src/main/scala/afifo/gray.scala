package afifo

import chisel3._

object bin2gray {
    def apply(n: UInt) = {
        n ^ (n >> 1)
    }
}

object fullInGray {
    def apply(wptr: UInt, rptr: UInt) = {
        val addrWidth = wptr.getWidth - 1
        (wptr(addrWidth,     addrWidth - 1) === ~rptr(addrWidth,     addrWidth - 1)) &&
        (wptr(addrWidth - 2, 0)             ===  rptr(addrWidth - 2, 0)),
    }
}

object emptyInGray {
    def apply(rptr: UInt, wptr: UInt) = {
        wptr === rptr
    }
}
