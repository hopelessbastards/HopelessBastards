local lib = require("packageToAI")

function turning( angle )
	return angle + 30;
end

function command(environment, state, fogOfWar)
	a = {}
	b = {}
	center = {}
	aicommand = {}
	
	
	aicommand[lib.up] = false
	aicommand[lib.down] = false
	aicommand[lib.left] = false
	aicommand[lib.right] = false
	
	a[1] = fogOfWar[3]
	a[2] = fogOfWar[4] / 2
	
	b[1] = environment[1][lib.x] + environment[1][lib.width] / 2
	b[2] = environment[1][lib.y] + environment[1][lib.height] / 2
	
	center[1] = fogOfWar[3] / 2
	center[2] = fogOfWar[4] / 2
	
	local newAngle = lib.gotTheAngleBetweenTwoPoint(a, b, center)
	
	if newAngle < 0 then
		newAngle = newAngle + 360
	end
	
	if math.abs(newAngle - state) > 15 then
		if math.abs(newAngle - state) < 180 then
			if newAngle - state < 0 then
				aicommand[lib.left] = true
			else
				aicommand[lib.right] = true
			end
		else
			if newAngle - state > 0 then
				aicommand[lib.left] = true
			else
				aicommand[lib.right] = true
			end
		end
		
		
	end

	
	
	
	return aicommand
	
end