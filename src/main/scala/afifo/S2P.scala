package afifo

import chisel3._
import chisel3.util.log2Up

import chisel3.experimental.chiselName

@chiselName
class S2P[T <: Data](private val gen: T, depth: Int) extends RawModule {

    val addrWidth = log2Up(depth)
    
    val w = IO(Flipped(new MemWritePortIO(gen, addrWidth)))
    val r = IO(Flipped(new MemReadPortIO(gen, addrWidth)))
    
    val storeType = UInt(gen.cloneType.getWidth.W)
    
    val mem = Mem(depth, storeType.cloneType)
    
    withClock(w.wctrl.wclk) {
        when(w.wctrl.we) {
            mem.write(w.wctrl.waddr, w.wbits.asUInt)
        }
    }
    
    withClock(r.rctrl.rclk) {
        val raddrReg = RegNext(r.rctrl.raddr)
        val rbitsReg = Reg(storeType.cloneType)
        rbitsReg := mem(raddrReg)
        r.rbits := rbitsReg.asTypeOf(gen.cloneType)
    }
    
}
