package data;

public interface Data {
	int[][][][] TEST = {
			{
				{
					{0,1,1},
					{0,1,0},
					{0,1,0},
				},
				{
					{0,1,1},
					{1,1,1},
					{1,1,0},
				},
			},
			{
				{
					{2,2,2},
					{2,2,2},
					{0,2,2},
				},
			},
			{
				{
					{3},
					{3},
				},
				{
					{3},
					{0},
				},
				{
					{3},
					{0},
				},
			},
			{
				{
					{4},
					{4},
				},
				{
					{4},
					{0},
				},
				{
					{4},
					{0},
				},
			},
	};

//	int[][][] SIDES6 = {
//			{
//				{0,0,0},
//				{0,3,0},
//				{0,0,0},
//			},
//			{
//				{0,6,0},
//				{4,0,2},
//				{0,5,0},
//			},
//			{
//				{0,0,0},
//				{0,1,0},
//				{0,0,0},
//			},
//	};

	int[][][] CUBE1 = {
		{
			{3,3,2},
			{3,1,2},
			{3,2,2},
		},
		{
			{3,4,2},
			{1,1,5},
			{1,5,5},
		},
		{
			{4,4,4},
			{4,5,4},
			{1,5,5},
		},
	};

	int[][][] CUBE3 = {
		{
			{1,1,1},
			{2,1,3},
			{2,2,2},
		},
		{
			{5,5,4},
			{6,6,3},
			{6,3,3},
		},
		{
			{5,4,4},
			{7,7,4},
			{6,7,7},
		},
	};

	int[][][][] PIECES3 = {
			{
				{
					{1},
					{0},
				},
				{
					{1},
					{1},
				},
				{
					{1},
					{0},
				},
			},
			{
				{
					{2},
					{0},
					{0},
				},
				{
					{2},
					{2},
					{2},
				},
			},
			{
				{
					{00},
					{30},
				},
				{
					{33},
					{30},
				},
			},
			{
				{
					{40},
					{00},
				},
				{
					{44},
					{40},
				},
			},
			{
				{
					{55},
					{50},
				},
			},
			{
				{
					{06},
					{66},
				},
				{
					{06},
					{00},
				},
			},
			{
				{
					{7},
					{0},
				},
				{
					{7},
					{7},
				},
				{
					{0},
					{7},
				},
			},
	};

	int[][][][] PIECES1 = {
		{//brown
			{
				{0,1,0},
				{0,1,1},
			},
			{
				{1,1,0},
				{0,0,0},
			},
		},
		{//cyan
			{
				{0,0,2},
				{2,2,2},
			},
			{
				{0,0,0},
				{2,0,0},
			}
		},
		{//orange
			{
				{3,0,0},
				{3,3,3},
			},
			{
				{0,0,0},
				{3,0,0},
			},
		},
		{//red
			{
				{4,0,4},
				{4,4,4},
			},
			{
				{0,0,0},
				{0,4,0},
			},
		},
		{//white
			{
				{0,5},
				{5,5},
			},
			{
				{5,0},
				{5,5},
			},
		},

	};

//	255
//	254
//	224
//	---
//	255
//	115
//	344
//	---
//	311
//	314
//	334
}

//511
//513
//342
//---
//551
//333
//342
//---
//421
//422
//442

//444
//454
//155
//---
//342
//115
//155
//---
//332
//312
//322
//
//113
//413
//433
//---
//552
//511
//443
//---
//552
//452
//422
//
//333
//113
//144
//---
//213
//514
//554
//---
//222
//552
//544
//
//544
//552
//222
//---
//554
//514
//213
//---
//144
//113
//333
//
//551
//454
//444
//---
//551
//511
//243
//---
//223
//213
//233
//
//255
//254
//224
//---
//255
//115
//344
//---
//311
//314
//334
//
//441
//311
//333
//---
//455
//415
//312
//---
//445
//255
//222
//
//445
//455
//441
//---
//255
//415
//311
//---
//222
//312
//333
//
//334
//314
//311
//---
//344
//115
//255
//---
//224
//254
//255
//
//422
//452
//552
//---
//443
//511
//552
//---
//433
//413
//113
//
//144
//554
//544
//---
//113
//514
//552
//---
//333
//213
//222
//
//222
//255
//445
//---
//312
//415
//455
//---
//333
//311
//441
