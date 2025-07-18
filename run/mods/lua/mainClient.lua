function InitClient()
      javaClassList = {inventoryC,usefulC,visualizerC,inventoryManager,inputC}

      count = (count or 0) + 1
      print("[LUA_DEBUG] : Call n°" .. count) --numbers of lua init made from java

      for i,class in ipairs(javaClassList) do
            if class == nil then
                  io.write("[LUA_DEBUG_CLIENT] this java class failed to load!, you will not be able to use it in lua! : ")
                  print(class) -- I had no idea how to print userdata in a more simple way
            end
      end
end

function LuaStartClient() --This is being called after a /lua_client_start command, called once but you can always use a [while true]
      print("[LUA_DEBUG] Client started")
end

function DoStuff()
      print("Did Stuff")
end
