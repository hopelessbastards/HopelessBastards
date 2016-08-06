local M = {}

M.x = 1
M.y = 2
M.width = 3
M.height = 4
M.type = 5
M.isEnemy = 6
M.healt = 7
M.maxHealth = 8
M.power = 9
M.maxPower = 10

M.left = 1
M.right = 2
M.up = 3
M.down = 4


M.moveToOrigo = moveToOrigo
M.rotatePointCenterOfOrigo = rotatePointCenterOfOrigo
M.gotTheAngleBetweenTwoPoint = gotTheAngleBetweenTwoPoint

function moveToOrigo(a, b, center)
	local amoved = {}
	local bmoved = {}

	amoved[1] = a[1] - center[1]
	amoved[2] = a[2] - center[2]
	
	bmoved[1] = b[1] - center[1]
	bmoved[2] = b[2] - center[2]
	
	return amoved, bmoved
end


function rotatePointCenterOfOrigo(a, b)
	local skalar = a[1] * b[1] + a[2] * b[2]
	lenghtA = math.sqrt(a[1] * a[1] + a[2] * a[2])
	lenghtB = math.sqrt(b[1] * b[1] + b[2] * b[2])
	cosAlpha = skalar / (lenghtA * lenghtB)
	alpha = math.acos(cosAlpha)
	return math.deg(math.acos(cosAlpha))
end


--Elõjelesen visszaadja, a bezárt szöget."a" az a pont, amihez "b" -t vizsgáljuk. Az elfordulásnak van elõjele.Ha negatív akkor a felsõ tartomány,ha pozitív akkor az alsó.
function gotTheAngleBetweenTwoPoint(a, b, center)
	if b[2] < center[2] then
		return rotatePointCenterOfOrigo(moveToOrigo(a, b, center)) * -1
	else
		return rotatePointCenterOfOrigo(moveToOrigo(a, b, center))
	end

end



return M